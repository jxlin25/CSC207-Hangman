package network;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class HangmanClient extends WebSocketClient {

    private final int roomId;

    public HangmanClient(int roomId) {
        super(URI.create("ws://localhost:8080"));
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
