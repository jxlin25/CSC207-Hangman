package interface_adapter.Room;
import view.RoomJoinView;
import use_case.Room.RoomJoinInteractor;

import java.util.concurrent.atomic.AtomicBoolean;

public class RoomJoinController implements RoomJoinView.Controller {
    private RoomJoinInteractor interactor;

    public RoomJoinController(RoomJoinInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void onJoinRoom(int roomId) {
        // Your actual business logic here
//        interactor.joinRoom(roomId);
//
        interactor.checkRoomExists(roomId, new RoomJoinInteractor.RoomCheckCallback() {
            @Override
            public void onRoomChecked(int roomId, boolean exists) {
                if (exists) {
                    System.out.println("Room " + roomId + " exists!");
                    interactor.joinRoom(roomId);
                } else {
                    System.out.println("Room " + roomId + " does not exist!");
                }
            }

            @Override
            public void onError(String message) {
                System.out.println("Error: " + message);
            }
        });
        // Or whatever your controller needs to do
    }

    public void onCreateRoom() {
        createRoomWithRetry(3, 1);
    }

    public void createRoomWithRetry(int maxAttempt, int currentAttempt) {
        if (currentAttempt > maxAttempt) {
            System.out.println("Failed to create room after " + currentAttempt + " attempts");
            // for all messages, try to reroute or deliver it back to the view
        }
        int roomId = (int) (Math.random() * 9000) + 1000;
        interactor.checkRoomExists(roomId, new RoomJoinInteractor.RoomCheckCallback() {
            @Override
            public void onRoomChecked(int roomId, boolean exists) {
                if (!exists) {
                    interactor.createRoom(roomId);
                } else {
                    System.out.println("Room id exists, trying a different one...");
                    createRoomWithRetry(maxAttempt, currentAttempt + 1);
                }
            }

            @Override
            public void onError(String message) {
                System.out.println("error: " + message);
                System.out.println("retrying...");
                createRoomWithRetry(maxAttempt, currentAttempt + 1);
            }
        });

    }


//    public void onCreateRoom() {
//        AtomicBoolean flag = new AtomicBoolean(true);
//        while (flag.get()) {
//            int random4Digit = (int) (Math.random() * 9000) + 1000;
//            interactor.checkRoomExists(random4Digit, new RoomJoinInteractor.RoomCheckCallback() {
//                @Override
//                public void onRoomChecked(int roomId, boolean exists) {
//                    if (exists) {
//                        System.out.println("Room " + roomId + " exists, trying another...");
//                    } else {
//                        flag.set(false);
//                        interactor.joinRoom(roomId);  // ← Use the parameter from callback!
//                        System.out.println("Created and joined room: " + roomId);
//                    }
//                }
//
//                @Override
//                public void onError(String message) {
//                    System.out.println("Error: " + message);
//                    flag.set(false);
//                }
//            });
//        }
//    }


}