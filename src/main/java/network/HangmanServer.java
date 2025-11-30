package network;

import entity.Player;
import entity.game_session.GameState;
import entity.game_session.GamePhase;
import use_case.game_session.GameInteractor; // Import GameInteractor
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import java.net.InetSocketAddress;
import java.util.*;

public class HangmanServer extends WebSocketServer {

    // Room management
    private final Map<String, Set<WebSocket>> rooms = new HashMap<>();
    private final Set<String> establishedRooms = new HashSet<>();
    private final Map<String, WebSocket> roomHosts = new HashMap<>();
    private final Map<String, Map<WebSocket, Player>> roomPlayers = new HashMap<>();

    // Game state management
    private final Map<String, GameState> gameStates = new HashMap<>();
    // Updated to map WebSocket to username directly for playerId
    private final Map<WebSocket, String> connectionToPlayerId = new HashMap<>();
    
    // Game Interactor
    private final GameInteractor gameInteractor = new GameInteractor(); // Initialize GameInteractor

    public HangmanServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New connection: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        // Remove from all rooms
        rooms.values().forEach(set -> set.remove(conn));
        // Remove from roomHosts if this connection was a host
        roomHosts.entrySet().removeIf(entry -> entry.getValue().equals(conn));
        // Remove player mapping
        connectionToPlayerId.remove(conn); // Use conn directly as key
        System.out.println("Closed: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Map<String, String> msg = parse(message);
        String type = msg.get("type");
        String room = msg.get("room");
        String username = msg.get("username"); // Assuming username is always sent in messages

        switch (type) {
            case "check_room" -> handleCheckRoom(conn, room);
            case "create" -> handleCreateRoom(conn, room, username);
            case "join" -> handleJoin(conn, room, username);
            // New cases for multiplayer game actions
            case "submit_word" -> handleSubmitWord(conn, room, msg.get("word"));
            case "make_guess" -> handleMakeGuess(conn, room, msg.get("letter").charAt(0)); // Get char directly
            case "start_game" -> handleStartGame(conn, room);
            case "state" -> broadcastToRoom(room, message); // Existing functionality
        }
    }

    private void handleCheckRoom(WebSocket conn, String room) {
        boolean exists = establishedRooms.contains(room);
        conn.send("{\"type\":\"room_check\",\"room\":\"" + room + "\",\"exists\":" + exists + "}");
    }

    private void handleCreateRoom(WebSocket conn, String room, String username) {
        if (establishedRooms.contains(room)) {
            conn.send("{\"type\":\"error\",\"message\":\"Room already exists\"}");
            return;
        }

        establishedRooms.add(room);
        rooms.putIfAbsent(room, new HashSet<>());
        rooms.get(room).add(conn);
        roomHosts.put(room, conn);
        System.out.println("SERVER DEBUG: Host set for room " + room + ": " + conn.toString()); // DEBUG

        // Initialize player mapping
        initializePlayerInRoom(conn, room, username);

        conn.send("{\"type\":\"created\",\"room\":\"" + room + "\",\"isHost\":true}");
    }

    private void handleJoin(WebSocket conn, String room, String username) {
        if (!establishedRooms.contains(room)) {
            conn.send("{\"type\":\"error\",\"message\":\"Room does not exist\"}");
            return;
        }
        
        Set<WebSocket> roomMembers = rooms.get(room);
        if (roomMembers != null && roomMembers.size() >= 2) {
            conn.send("{\"type\":\"error\",\"message\":\"Room full\"}");
            return;
        }

        rooms.putIfAbsent(room, new HashSet<>());
        rooms.get(room).add(conn);

        // Initialize player mapping
        initializePlayerInRoom(conn, room, username);

        conn.send("{\"type\":\"joined\",\"room\":\"" + room + "\",\"isHost\":false}");

        WebSocket hostConn = roomHosts.get(room);
        if (hostConn != null && hostConn.isOpen()) {
            hostConn.send("{\"type\":\"player_joined_notification\",\"room\":\"" + room + "\",\"username\":\"" + username + "\"}");
        }
    }

    // New game message handlers
    private void handleStartGame(WebSocket conn, String room) {
        System.out.println("SERVER DEBUG: handleStartGame called by: " + conn.toString() + " for room: " + room); // DEBUG
        System.out.println("SERVER DEBUG: Checking if " + conn.toString() + " is host of " + room); // DEBUG
        if (!isRoomHost(conn, room)) {
            conn.send("{\"type\":\"error\",\"message\":\"Only host can start game\"}");
            return;
        }

        Map<WebSocket, Player> playersInRoom = roomPlayers.get(room);
        if (playersInRoom == null || playersInRoom.size() != 2) {
            conn.send("{\"type\":\"error\",\"message\":\"Need exactly 2 players to start game\"}");
            return;
        }

        GameState gameState = initializeNewGameState(room);
        gameStates.put(room, gameState);
        
        // Broadcast initial game state
        broadcastGameState(room, gameState);
    }

    private void handleSubmitWord(WebSocket conn, String room, String word) {
        GameState currentState = gameStates.get(room);
        if (currentState == null) {
            conn.send("{\"type\":\"error\",\"message\":\"Game not started\"}");
            return;
        }

        String playerId = getPlayerId(conn); // Get playerId (username)
        if (playerId == null) {
            conn.send("{\"type\":\"error\",\"message\":\"Player not registered\"}");
            return;
        }

        try {
            // Use GameInteractor to process word submission
            GameState updatedState = gameInteractor.processWordSubmission(currentState, playerId, word);
            gameStates.put(room, updatedState); // Update the game state in the map
            broadcastGameState(room, updatedState);
        } catch (Exception e) {
            conn.send("{\"type\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    private void handleMakeGuess(WebSocket conn, String room, char letter) {
        GameState currentState = gameStates.get(room);
        if (currentState == null) {
            conn.send("{\"type\":\"error\",\"message\":\"Game not started\"}");
            return;
        }

        String playerId = getPlayerId(conn); // Get playerId (username)
        if (playerId == null) {
            conn.send("{\"type\":\"error\",\"message\":\"Player not registered\"}");
            return;
        }

        try {
            // Use GameInteractor to process guess
            GameState updatedState = gameInteractor.processGuess(currentState, playerId, letter);
            gameStates.put(room, updatedState); // Update the game state in the map
            broadcastGameState(room, updatedState);
        } catch (Exception e) {
            conn.send("{\"type\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    // Helper methods
    private void initializePlayerInRoom(WebSocket conn, String room, String username) {
        // Use username as the playerId
        connectionToPlayerId.put(conn, username); // Map WebSocket to username

        roomPlayers.putIfAbsent(room, new HashMap<>());
        Player player = new Player(username); // Create Player with username
        roomPlayers.get(room).put(conn, player);
    }

    private String getPlayerId(WebSocket conn) {
        return connectionToPlayerId.get(conn);
    }

    private boolean isRoomHost(WebSocket conn, String room) {
        WebSocket host = roomHosts.get(room);
        System.out.println("SERVER DEBUG: isRoomHost check: conn=" + conn.toString() + ", hostInMap=" + (host != null ? host.toString() : "null")); // DEBUG
        return host != null && host.equals(conn);
    }

    private GameState initializeNewGameState(String room) {
        Map<WebSocket, Player> playersMap = roomPlayers.get(room);
        List<Player> playerList = new ArrayList<>(playersMap.values());

        // Ensure playerList has at least two players for word setter/guesser assignment
        if (playerList.size() < 2) {
            throw new IllegalStateException("Not enough players to initialize game state.");
        }

        // Convert room string to integer ID (you might want to handle this differently)
        int roomId;
        try {
            roomId = Integer.parseInt(room);
        } catch (NumberFormatException e) {
            roomId = room.hashCode(); // Fallback to hash code if not a valid int
        }

        // Create initial GameState
        GameState gameState = new GameState(roomId, playerList);
        
        // Assign word setter and guesser (e.g., host is word setter, other player is guesser)
        // This assumes playerList[0] is host, playerList[1] is other player
        gameState.setCurrentWordSetterId(playerList.get(0).getId());
        gameState.setCurrentGuesserId(playerList.get(1).getId());
        gameState.setCurrentPhase(GamePhase.WORD_SELECTION); // Initial phase
        
        return gameState;
    }

    private void broadcastGameState(String room, GameState gameState) {
        String gameStateJson = gameStateToJson(gameState);
        // Using "game_state_update" as type as per common practice
        broadcastToRoom(room, "{\"type\":\"game_state_update\",\"gameState\":" + gameStateJson + "}");
    }

    private String gameStateToJson(GameState gameState) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"roomId\":").append(gameState.getRoomId()).append(",");
        json.append("\"currentPhase\":\"").append(gameState.getCurrentPhase()).append("\",");
        json.append("\"currentRound\":").append(gameState.getCurrentRound()).append(",");
        json.append("\"maxRounds\":").append(gameState.getMaxRounds()).append(",");
        json.append("\"currentWordSetterId\":\"").append(gameState.getCurrentWordSetterId()).append("\",");
        json.append("\"currentGuesserId\":\"").append(gameState.getCurrentGuesserId()).append("\",");
        json.append("\"secretWord\":").append(gameState.getSecretWord() != null ? "\"" + gameState.getSecretWord() + "\"" : "null").append(","); // Include secret word for now
        json.append("\"revealedWord\":\"").append(gameState.getRevealedWord()).append("\",");
        json.append("\"guessedLetters\":").append(setToJsonArray(gameState.getGuessedLetters())).append(",");
        json.append("\"incorrectGuessesCount\":").append(gameState.getIncorrectGuessesCount()).append(",");
        json.append("\"maxIncorrectGuesses\":").append(gameState.getMaxIncorrectGuesses()).append(",");
        json.append("\"roundWinnerId\":").append(gameState.getRoundWinnerId() != null ? "\"" + gameState.getRoundWinnerId() + "\"" : "null").append(",");
        json.append("\"scores\":{"); // Start scores object
        boolean firstScore = true;
        for (Map.Entry<String, Integer> entry : gameState.getScores().entrySet()) {
            if (!firstScore) json.append(",");
            json.append("\"").append(entry.getKey()).append("\":").append(entry.getValue());
            firstScore = false;
        }
        json.append("},"); // End scores object

        // Add players information
        json.append("\"players\":").append(playersToJsonArray(gameState.getPlayers()));
        json.append("}");
        return json.toString();
    }

    private String setToJsonArray(Set<Character> set) {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (Character c : set) {
            if (!first) sb.append(",");
            sb.append("\"").append(c).append("\"");
            first = false;
        }
        sb.append("]");
        return sb.toString();
    }

    private String playersToJsonArray(List<Player> players) {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (Player player : players) {
            if (!first) sb.append(",");
            sb.append("{\"id\":\"").append(player.getId()).append("\",\"name\":\"").append(player.getName()).append("\"}"); // Changed "username" to "name" to match Player entity
            first = false;
        }
        sb.append("]");
        return sb.toString();
    }

    private void broadcastToRoom(String room, String msg) {
        Set<WebSocket> members = rooms.get(room);
        if (members != null) {
            for (WebSocket ws : members) ws.send(msg);
        }
    }

    private Map<String, String> parse(String json) {
        Map<String, String> result = new HashMap<>();
        try {
            json = json.trim();
            if (json.startsWith("{") && json.endsWith("}")) {
                json = json.substring(1, json.length() - 1);
                String[] pairs = json.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                for (String pair : pairs) {
                    String[] keyValue = pair.split(":(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim().replaceAll("\"", "");
                        String value = keyValue[1].trim().replaceAll("\"", "");
                        result.put(key, value);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("SERVER: Error parsing JSON: " + json + " - " + e.getMessage());
        }
        return result;
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Hangman server started!");
    }

    public Set<String> getEstablishedRooms() {
        return new HashSet<>(establishedRooms);
    }

    public static void main(String[] args) {
        new HangmanServer(8080).start();
    }
}