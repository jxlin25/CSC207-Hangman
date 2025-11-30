package network;

import entity.game_session.GameState;
import entity.game_session.GamePhase;
import entity.Player;
import interface_adapter.MakeGuess.MakeGuessViewModel;
import interface_adapter.Room.RoomJoinPresenter;
import interface_adapter.Room.RoomJoinViewModel;
import interface_adapter.SubmitWord.SubmitWordViewModel;
import interface_adapter.ViewManagerModel;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.Room.RoomJoinOutputData;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HangmanClient extends WebSocketClient {
    private int roomId;
    private String playerId;
    private final ViewManagerModel viewManagerModel;
    private final RoomJoinViewModel roomJoinViewModel;
    private final RoomJoinPresenter roomJoinPresenter;
    private final MakeGuessViewModel makeGuessViewModel; // Added MakeGuessViewModel for state updates

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("CLIENT: Connection opened successfully to " + this.getURI());
    }

    public HangmanClient(
            ViewManagerModel viewManagerModel,
            RoomJoinViewModel roomJoinViewModel,
            RoomJoinPresenter roomJoinPresenter,
            MakeGuessViewModel makeGuessViewModel // Added MakeGuessViewModel to constructor
    ) throws URISyntaxException {

        super(new URI("ws://localhost:8080"));

        this.viewManagerModel = viewManagerModel;
        this.roomJoinViewModel = roomJoinViewModel;
        this.roomJoinPresenter = roomJoinPresenter;
        this.makeGuessViewModel = makeGuessViewModel; // Initialize MakeGuessViewModel
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received: " + message);
        // Simple parsing for JSON messages from the server
        Map<String, String> parsedMessage = parse(message); // Using org.json for parsing
        String type = parsedMessage.get("type"); // No, parsedMessage now from parse method (manual)
        
        // Use JSONObject for parsing for robustness
        JSONObject jsonMessage;
        try {
            jsonMessage = new JSONObject(message);
        } catch (Exception e) {
            System.err.println("CLIENT: Error parsing message to JSONObject: " + message + " - " + e.getMessage());
            return;
        }

        type = jsonMessage.optString("type");
        String room = jsonMessage.optString("room");
        String errorMessage = jsonMessage.optString("message");


        if ("error".equals(type)) {
            System.err.println("Server Error: " + errorMessage);
            roomJoinViewModel.getState().setError(errorMessage);
            roomJoinViewModel.firePropertyChange();
        } else if ("created".equals(type) || "joined".equals(type)) {
            try {
                int receivedRoomId = Integer.parseInt(room);
                boolean isHost = jsonMessage.optBoolean("isHost");

                Player currentPlayer = new Player("You"); // Placeholder player
                currentPlayer.setHost(isHost); // Set host status
                this.playerId = currentPlayer.getId(); // Set playerId here
                List<Player> players = Collections.singletonList(currentPlayer);
                setRoomId(receivedRoomId);
                
                // Also update the currentClientId in MakeGuessState
                makeGuessViewModel.getState().setCurrentClientId(this.playerId);
                makeGuessViewModel.getState().setMultiplayerGame(true); // Indicate it's multiplayer
                makeGuessViewModel.getState().setRoomId(receivedRoomId); // Added this line

                RoomJoinOutputData successData = new RoomJoinOutputData(receivedRoomId, currentPlayer, players);
                roomJoinPresenter.prepareSuccess(successData);
                
            } catch (NumberFormatException e) {
                System.err.println("CLIENT: Error parsing room ID from server response: " + room);
                roomJoinViewModel.getState().setError("Error joining/creating room: Invalid room ID from server.");
                roomJoinViewModel.firePropertyChange();
            }

        } else if ("game_state_update".equals(type)) {
            System.out.println("CLIENT DEBUG: Received GAME_STATE_UPDATE for room: " + room); // DEBUG
            JSONObject gameStateJson = jsonMessage.optJSONObject("gameState");
            if (gameStateJson != null) {
                GameState gameState = parseGameState(gameStateJson);
                handleGameStateUpdate(gameState);
            }
        }
    }

    private void handleGameStateUpdate(GameState gameState) {
        System.out.println("CLIENT DEBUG: handleGameStateUpdate called with GameState: " + gameState.getCurrentPhase()); // DEBUG
        // Use the new method to set state from GameState
        makeGuessViewModel.setStateFromGameState(gameState, this.playerId);
        System.out.println("CLIENT DEBUG: MakeGuessViewModel state updated. Current Phase: " + makeGuessViewModel.getState().getRoundStatus()); // DEBUG

        // Fire property change to update MakeGuessView
        System.out.println("CLIENT DEBUG: Firing property change for MakeGuessViewModel."); // DEBUG
        makeGuessViewModel.firePropertyChange();

        // Decide which view to show based on game phase and player role
        if (gameState.getCurrentPhase() == GamePhase.WORD_SELECTION) {
            if (playerId != null && playerId.equals(gameState.getCurrentWordSetterId())) {
            viewManagerModel.setState(SubmitWordViewModel.VIEW_NAME);
            } else {
                // Guesser/Spectator waits, MakeGuessView will show appropriate waiting message
                viewManagerModel.setState(MakeGuessViewModel.VIEW_NAME);
            }
        } else if (gameState.getCurrentPhase() == GamePhase.GUESSING ||
                   gameState.getCurrentPhase() == GamePhase.ROUND_END ||
                   gameState.getCurrentPhase() == GamePhase.GAME_OVER) {
            viewManagerModel.setState(MakeGuessViewModel.VIEW_NAME);
        }
        System.out.println("CLIENT DEBUG: Firing property change for ViewManagerModel. Active view: " + viewManagerModel.getState()); // DEBUG
        viewManagerModel.firePropertyChange(); // Notify ViewManager to switch views
    }


    private GameState parseGameState(JSONObject gameStateJson) {
        System.out.println("CLIENT DEBUG: Parsing GameState JSON."); // DEBUG
        int roomId = gameStateJson.optInt("roomId");
        GamePhase currentPhase = GamePhase.valueOf(gameStateJson.optString("currentPhase"));
        int currentRound = gameStateJson.optInt("currentRound");
        int maxRounds = gameStateJson.optInt("maxRounds");
        String currentWordSetterId = gameStateJson.optString("currentWordSetterId");
        String currentGuesserId = gameStateJson.optString("currentGuesserId");
        String secretWord = gameStateJson.optString("secretWord", null); // Can be null
        String revealedWord = gameStateJson.optString("revealedWord");
        int incorrectGuessesCount = gameStateJson.optInt("incorrectGuessesCount");
        int maxIncorrectGuesses = gameStateJson.optInt("maxIncorrectGuesses");
        String roundWinnerId = gameStateJson.optString("roundWinnerId", null); // Can be null

        // Parse guessedLetters
        Set<Character> guessedLetters = new HashSet<>();
        JSONArray guessedLettersJson = gameStateJson.optJSONArray("guessedLetters");
        if (guessedLettersJson != null) {
            for (int i = 0; i < guessedLettersJson.length(); i++) {
                guessedLetters.add(guessedLettersJson.optString(i).charAt(0));
            }
        }

        // Parse scores
        Map<String, Integer> scores = new HashMap<>();
        JSONObject scoresJson = gameStateJson.optJSONObject("scores");
        if (scoresJson != null) {
            for (String key : scoresJson.keySet()) {
                scores.put(key, scoresJson.optInt(key));
            }
        }
        
        // Parse players
        List<Player> players = new ArrayList<>();
        JSONArray playersJson = gameStateJson.optJSONArray("players");
        if (playersJson != null) {
            for (int i = 0; i < playersJson.length(); i++) {
                JSONObject playerJson = playersJson.optJSONObject(i);
                if (playerJson != null) {
                    Player player = new Player(playerJson.optString("name")); // Assuming 'name' is player name
                    player.setId(playerJson.optString("id")); // Set player ID
                    // Assuming 'id' is in the JSON for the Player object
                    players.add(player);
                }
            }
        }
        
        // Manually construct GameState object (assuming a constructor that takes all these)
        // Adjust GameState constructor or use setters if it's not a single massive constructor
        GameState gameState = new GameState(roomId, players); // Assuming this constructor
        gameState.setCurrentPhase(currentPhase);
        gameState.setCurrentRound(currentRound);
        // gameState.setMaxRounds(maxRounds); // No setter for maxRounds
        gameState.setCurrentWordSetterId(currentWordSetterId);
        gameState.setCurrentGuesserId(currentGuesserId);
        gameState.setSecretWord(secretWord);
        gameState.setRevealedWord(revealedWord);
        gameState.setGuessedLetters(guessedLetters);
        gameState.setIncorrectGuessesCount(incorrectGuessesCount);
        // gameState.setMaxIncorrectGuesses(maxIncorrectGuesses); // No setter for maxIncorrectGuesses
        gameState.setRoundWinnerId(roundWinnerId);
        // Scores map is immutable after creation in current GameState constructor.
        // If scores are updated in GameState after creation, they must be set via setter.
        // For now, assuming scores are set during GameState construction or updated via addScore method in GameState.
        // Since GameState.getScores() returns a reference to the map, we can update it directly:
        gameState.getScores().clear();
        gameState.getScores().putAll(scores);
        System.out.println("CLIENT DEBUG: GameState parsed. Current Phase: " + gameState.getCurrentPhase()); // DEBUG

        return gameState;
    }

    // Existing parse method - keep for other message types that use simple string parsing
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
            System.err.println("CLIENT: Error parsing JSON: " + json + " - " + e.getMessage());
        }
        return result;
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("CLIENT: Connection closed by " + (remote ? "remote peer" : "us") + ". Code: " + code + ", Reason: " + reason);
    }


    @Override
    public void onError(Exception ex) {
        System.err.println("CLIENT: An error occurred: " + ex.getMessage());
        ex.printStackTrace();
    }

    public String getPlayerId() {
        return playerId;
    }
}
