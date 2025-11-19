package use_case.Room;
import network.HangmanServer;
import network.HangmanClient;
import org.java_websocket.handshake.ServerHandshake;

public class RoomJoinInteractor {
    public static void main(String[] args) {

    }
    public void checkRoomExists(int roomId, RoomCheckCallback callback) {
        try {
            HangmanClient client = new HangmanClient(roomId) {
                @Override
                public void onMessage(String message) {
                    // Parse the server response
                    if (message.contains("\"type\":\"room_check\"")) {
                        boolean exists = message.contains("\"exists\":true");

                        callback.onRoomChecked(roomId, exists);
                        this.close(); // Close the check connection
                    }
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    // Once connected, ask server to check the room
                    this.send("{\"type\":\"check_room\",\"room\":\"" + roomId + "\"}");
                }
            };

            client.connect(); // Start the connection

        } catch (Exception e) {
            callback.onError("Connection failed: " + e.getMessage());
        }
    }
    // Create these in your project
    public interface RoomCheckCallback {
        void onRoomChecked(int roomId, boolean exists);
        void onError(String message);
    }

    public interface RoomJoinCallback {
        void onJoinSuccess(int roomId);
        void onError(String message);
    }

    public interface RoomCreateCallback {
        void onCreateSuccess(int roomId);
        void onError(String message);
    }

    public void joinRoom(int roomId){
        try {
            HangmanClient c = new HangmanClient(roomId);
            c.connectBlocking();
            System.out.println("successfully connected???");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void createRoom(int roomId) {
        try {
            HangmanClient client = new HangmanClient(roomId) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("Connected to server");
                    send("{\"type\":\"create\", \"room\":\"" + roomId + "\"}");  // ← Send "create" instead of "join"
                }

                @Override
                public void onMessage(String message) {
                    System.out.println("Received: " + message);
                    if (message.contains("\"type\":\"created\"")) {
                        System.out.println("Room created successfully: " + roomId);
                    } else if (message.contains("\"type\":\"error\"")) {
                        System.out.println("Room creation failed: " + message);
                    }
                }
            };

            client.connectBlocking();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}