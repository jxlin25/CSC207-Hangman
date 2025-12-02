package use_case.room;

import javax.swing.SwingUtilities;

import org.java_websocket.handshake.ServerHandshake;

import network.HangmanClient;
import view.LobbyView;

public class RoomJoinInteractor {
    /**
     * Checks whether a room with the given ID exists.
     *
     * @param roomId   the ID of the room to check
     * @param callback the callback to invoke with the result
     * @throws Exception if the client fails to initialize or connect
     */
    public void checkRoomExists(int roomId, RoomCheckCallback callback) {

        final HangmanClient client = new HangmanClient(roomId) {
            @Override
            public void onMessage(String message) {
                if (message.contains("\"type\":\"room_check\"")) {
                    final boolean exists = message.contains("\"exists\":true");
                    callback.onRoomChecked(roomId, exists);
                    this.close();
                }
            }

            @Override
            public void onOpen(ServerHandshake handshake) {
                this.send("{\"type\":\"check_room\",\"room\":\"" + roomId);
            }
        };
        client.connect();
    }

    /**
     * Attempts to create a room on the Hangman server.
     *
     * @param roomId   the ID of the room to join
     * @param username the username to join as
     * @throws Exception if the client fails to initialize or connect
     */
    public void createRoom(int roomId, String username) {

        final HangmanClient client = new HangmanClient(roomId) {
            @Override
            public void onOpen(ServerHandshake handshake) {
                handleOpen(handshake, roomId, username);
            }

            @Override
            public void onMessage(String message) {
                handleMessage(message, roomId, username, this);
            }
        };

        try {
            client.connectBlocking();
        }
        catch (InterruptedException error) {
            Thread.currentThread().interrupt();
            System.out.println(error);
        }
    }

    /**
     * Attempts to join a room on the Hangman server.
     *
     * @param roomId   the ID of the room to join
     * @param username the username to join as
     * @throws Exception if the client fails to initialize or connect
     */

    public void joinRoom(int roomId, String username) {

        final HangmanClient client = new HangmanClient(roomId) {
            @Override
            public void onOpen(ServerHandshake handshake) {
                handleOpen(handshake, roomId, username);
            }

            @Override
            public void onMessage(String message) {
                handleMessage(message, roomId, username, this);
            }
        };

        try {
            client.connectBlocking();
        }
        catch (InterruptedException error) {
            Thread.currentThread().interrupt();
            System.out.println(error);
        }
    }

    private void handleOpen(ServerHandshake handshake, int roomId, String finalUser) {
        System.out.println("Connected to server");
    }

    private void handleMessage(String message, int roomId, String finalUser, HangmanClient client) {
        System.out.println("Received: " + message);

        if (message.contains("\"type\":\"joined\"")) {
            SwingUtilities.invokeLater(() -> {
                new LobbyView(roomId, client, false, finalUser).setVisible(true);
            });
        }
        else if (message.contains("\"type\":\"error\"")) {
            System.out.println("Room join failed: " + message);
        }
    }

    /**
     * Callback interface for room existence checks.
     */
    public interface RoomCheckCallback {
        /**
         * Called when room check completes.
         *
         * @param roomId the room ID checked
         * @param exists whether the room exists
         */
        void onRoomChecked(int roomId, boolean exists);

        /**
         * Called when an error occurs during room check.
         *
         * @param message error message
         */
        void onError(String message);
    }

    public interface RoomJoinCallback {
        /**
         * Called when room join succeeds.
         *
         * @param roomId the joined room ID
         */
        void onJoinSuccess(int roomId);

        /**
         * Called when an error occurs during room join.
         *
         * @param message error message
         */
        void onError(String message);
    }

    public interface RoomCreateCallback {
        /**
         * Called when room creation succeeds.
         *
         * @param roomId the created room ID
         */
        void onCreateSuccess(int roomId);

        /**
         * Called when an error occurs during room creation.
         *
         * @param message error message
         */
        void onError(String message);
    }

}
