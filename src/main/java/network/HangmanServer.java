package network;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;
import java.util.*;




public class HangmanServer extends WebSocketServer {

    // roomId -> set of sockets
    private final Map<String, Set<WebSocket>> rooms = new HashMap<>();
    private final Set<String> establishedRooms = new HashSet<>();

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
        System.out.println("Closed: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        /*
            Expected JSON messages:
            { "type": "join", "room": "1234" }
            { "type": "guess", "room": "1234", "letter": "A" }
        */

        Map<String, String> msg = parse(message);
        String type = msg.get("type");
        String room = msg.get("room");

        switch (type) {
            case "check_room" -> handleCheckRoom(conn, room);
            case "create" -> handleCreateRoom(conn, room);
            case "join" -> handleJoin(conn, room);
            case "guess" -> broadcastToRoom(room, message);
            case "state" -> broadcastToRoom(room, message); // for spectators
        }
    }

    private void handleCheckRoom(WebSocket conn, String room) {
        boolean exists = establishedRooms.contains(room);
        conn.send("{\"type\":\"room_check\",\"room\":\"" + room + "\",\"exists\":" + exists + "}");
    }

    private void handleCreateRoom(WebSocket conn, String room) {
        if (establishedRooms.contains(room)) {
            conn.send("{\"type\":\"error\",\"message\":\"Room already exists\"}");
            return;
        }

        establishedRooms.add(room);
        rooms.putIfAbsent(room, new HashSet<>());
        rooms.get(room).add(conn);
        conn.send("{\"type\":\"created\",\"room\":\"" + room + "\"}");
    }


    private void handleJoin(WebSocket conn, String room) {
        if (!establishedRooms.contains(room)) {
            conn.send("{\"type\":\"error\",\"message\":\"Room does not exist\"}");
            return;
        }

        int userCount = rooms.get(room).size();


        rooms.putIfAbsent(room, new HashSet<>());
        rooms.get(room).add(conn);
        conn.send("{\"type\":\"joined\",\"room\":\"" + room + "\"}");
        broadcastToRoom(room,
                "{\"type\":\"user_joined\",\"room\":\"" + room +
                        "\",\"userCount\":" + userCount +
                        ",\"message\":\"User joined. Total users: " + userCount + "\"}");
    }

    private void broadcastToRoom(String room, String msg) {
        Set<WebSocket> members = rooms.get(room);
        if (members != null) {
            for (WebSocket ws : members) ws.send(msg);
        }
    }

    private Map<String, String> parse(String json) {
        Map<String, String> result = new HashMap<>();
        json = json.replaceAll("[{}\"]", "");
        String[] parts = json.split(",");
        for (String p : parts) {
            String[] kv = p.split(":");
            if (kv.length == 2) result.put(kv[0].trim(), kv[1].trim());
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

