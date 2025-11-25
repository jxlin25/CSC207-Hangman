package use_case.Room;
import network.HangmanServer;
import network.HangmanClient;
import org.java_websocket.handshake.ServerHandshake;
import view.LobbyView;
import javax.swing.SwingUtilities;
import entity.Player;

public class RoomJoinInteractor {
    public static void main(String[] args) {

    }
    public void checkRoomExists(int roomId, RoomCheckCallback callback) {
        try {
            HangmanClient client = new HangmanClient(roomId) {
                @Override
                public void onMessage(String message) {
                    if (message.contains("\"type\":\"room_check\"")) {
                        boolean exists = message.contains("\"exists\":true");
                        callback.onRoomChecked(roomId, exists);
                        this.close();
                    }
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    this.send("{\"type\":\"check_room\",\"room\":\"" + roomId + "\"}");
                }
            };

            client.connect();

        } catch (Exception e) {
            callback.onError("Connection failed: " + e.getMessage());
        }
    }
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

    public void joinRoom(int roomId, String username) {
        final String finalUser = username;
//        final Player player = new Player(username);
//        player.setRoomId(roomId);

        try {
            HangmanClient client = new HangmanClient(roomId) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("Connected to server");
                    send("{\"type\":\"join\", \"room\":\"" + roomId + "\", \"username\":\"" + finalUser + "\"}");
                }

                @Override
                public void onMessage(String message) {
                    System.out.println("Received: " + message);
                    if (message.contains("\"type\":\"joined\"")) {
                        System.out.println("Room joined successfully: " + roomId);
                        System.out.println(finalUser);
                        SwingUtilities.invokeLater(() -> {
                            new LobbyView(roomId, this, false, finalUser).setVisible(true);
                        });
                    } else if (message.contains("\"type\":\"error\"")) {
                        System.out.println("Room join failed: " + message);

                    }
                }

            };

            client.connectBlocking();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void createRoom(int roomId, String username) {
        final String finalUser = username;
        try {
            HangmanClient client = new HangmanClient(roomId) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("Connected to server");
                    send("{\"type\":\"create\", \"room\":\"" + roomId + "\", \"username\":\"" + finalUser + "\"}");

                }

                @Override
                public void onMessage(String message) {
                    System.out.println("Received: " + message);
                    if (message.contains("\"type\":\"created\"")) {
                        System.out.println("Room created successfully: " + roomId);
                        System.out.println(finalUser);
                        SwingUtilities.invokeLater(() -> {
                            new LobbyView(roomId, this, true, finalUser).setVisible(true);
                        });
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