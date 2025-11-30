package network;

import entity.Player;
import entity.game_session.GameState;
import interface_adapter.GameSession.GameSessionController;
//import manager.GameSessionManager;
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import java.net.InetSocketAddress;
import java.util.*;
import entity.game_session.GamePhase;




public class HangmanServer extends WebSocketServer {

    // roomId -> set of sockets
    private final Map<String, Set<WebSocket>> rooms = new HashMap<>();
    private final Set<String> establishedRooms = new HashSet<>();
    private final Map<String, WebSocket> roomHosts = new HashMap<>(); // Track host for each room
    //private final Map<String, GameState> gameStates;
    private final Map<String, Map<WebSocket, Player>> roomPlayers = new HashMap<>();



    public HangmanServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New connection: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        // remove from all rooms
        rooms.values().forEach(set -> set.remove(conn));
        // Remove from roomHosts if this connection was a host
        roomHosts.entrySet().removeIf(entry -> entry.getValue().equals(conn));
        System.out.println("Closed: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        /*
            Expected JSON messages:
            { "type": "join", "room": "1234", "username": "Player1" }
            { "type": "guess", "room": "1234", "letter": "A" }
            { "type": "create", "room": "1234", "username": "HostPlayer" }
        */

        Map<String, String> msg = parse(message);
        String type = msg.get("type");
        String room = msg.get("room");
        String username = msg.get("username"); // Extract username

        switch (type) {
            case "check_room" -> handleCheckRoom(conn, room);
            case "create" -> handleCreateRoom(conn, room, username);
            case "join" -> handleJoin(conn, room, username);
//            case "submit_word" -> {
//                GameSessionController controller = GameSessionManager.getController(room);
//                controller.handleWordSubmission(senderId, msg.get("word"));
//            }
//            case "make_guess" -> {
//                GameSessionController controller = GameSessionManager.getController(room);
//                controller.handleGuess(senderId, msg.get("letter"));
//            }
            case "state" -> broadcastToRoom(room, message); // for spectators
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
        roomHosts.put(room, conn); // Store this connection as the host

        conn.send("{\"type\":\"created\",\"room\":\"" + room + "\",\"isHost\":true}");
    }


    private void handleJoin(WebSocket conn, String room, String username) {
        if (rooms.get(room).size() >= 2) { // Enforce 2-player limit
            conn.send("{\"type\":\"error\",\"message\":\"Room full\"}");
            return;
        }

        if (!establishedRooms.contains(room)) {
            conn.send("{\"type\":\"error\",\"message\":\"Room does not exist\"}");
            return;
        }

        // Add joining client to the room
        rooms.putIfAbsent(room, new HashSet<>());
        rooms.get(room).add(conn);

        // Send 'joined' response to the joining client (they are not the host)
        conn.send("{\"type\":\"joined\",\"room\":\"" + room + "\",\"isHost\":false}");

        // Notify the host (and other players if any) that a new player has joined
        WebSocket hostConn = roomHosts.get(room);
        if (hostConn != null && hostConn.isOpen()) {
            hostConn.send("{\"type\":\"player_joined_notification\",\"room\":\"" + room + "\",\"username\":\"" + username + "\"}");
        }
        // Also notify other clients in the room (if more than 2 players eventually)
        // For now, with 2-player limit, hostConn is the only other client.

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
            // A more robust way to parse flat JSON without a dedicated library
            json = json.trim();
            if (json.startsWith("{") && json.endsWith("}")) {
                json = json.substring(1, json.length() - 1); // Remove outer braces
                String[] pairs = json.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Split by comma outside quotes
                for (String pair : pairs) {
                    String[] keyValue = pair.split(":(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Split by colon outside quotes
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

