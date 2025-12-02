package interface_adapter.room;

import use_case.room.RoomJoinInteractor;
import view.RoomJoinView;

public class RoomJoinController implements RoomJoinView.Controller {
    private static final int MAX_ATTEMPT = 3;

    private RoomJoinInteractor interactor;
    private String username;
    private int currentAttempt = 1;

    public RoomJoinController(RoomJoinInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void onJoinRoom(int roomId, String inputUsername) {
        interactor.checkRoomExists(roomId, new RoomJoinInteractor.RoomCheckCallback() {
            @Override
            public void onRoomChecked(int roomId, boolean exists) {
                if (exists) {
                    System.out.println("Room " + roomId + " exists!");

                    interactor.joinRoom(roomId, inputUsername);
                }
                else {
                    System.out.println("Room " + roomId + " does not exist!");
                }
            }

            @Override
            public void onError(String message) {
                System.out.println("Error: " + message);
            }
        });
    }

    /**
     * Creates the room.
     * @param inputUsername the username you input.
     */
    public void onCreateRoom(String inputUsername) {
        createRoomWithRetry(MAX_ATTEMPT, currentAttempt, inputUsername);
    }

    /**
     * Room creation retry logic.
     * @param maxAttempt is the max attempts.
     * @param attempt is the current iteration of attempt.
     * @param inputUsername is the username.
     */
    public void createRoomWithRetry(int maxAttempt, int attempt, String inputUsername) {
        if (currentAttempt > maxAttempt) {
            System.out.println("Failed to create room after " + currentAttempt + " attempts");
        }
        final int roomId = (int) (Math.random() * 9000) + 1000;
        interactor.checkRoomExists(roomId, new RoomJoinInteractor.RoomCheckCallback() {
            @Override
            public void onRoomChecked(int roomId, boolean exists) {
                if (!exists) {
                    interactor.createRoom(roomId, inputUsername);
                }
                else {
                    System.out.println("Room id exists, trying a different one...");
                    createRoomWithRetry(maxAttempt, attempt + 1, inputUsername);
                }
            }

            @Override
            public void onError(String message) {
                System.out.println("error: " + message);
                System.out.println("retrying...");
                createRoomWithRetry(maxAttempt, attempt + 1, inputUsername);
            }
        });

    }

}