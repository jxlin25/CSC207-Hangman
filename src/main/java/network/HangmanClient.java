package network;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class HangmanClient extends WebSocketClient {
    private int roomId;
    public HangmanClient() throws Exception {
        super(new URI("ws://localhost:8080"));
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("Connected to server");
        send("{\"type\":\"join\", \"room\":\"" + roomId + "\"}");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received: " + message);
        // Update GUI or console
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
}
