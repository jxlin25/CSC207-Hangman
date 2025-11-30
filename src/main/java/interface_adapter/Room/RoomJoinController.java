package interface_adapter.Room;
import view.RoomJoinView;
import use_case.Room.RoomJoinInteractor;

public class RoomJoinController implements RoomJoinView.Controller {
    private RoomJoinInteractor interactor;
    private String username;

    public RoomJoinController(RoomJoinInteractor interactor) {
        this.interactor = interactor;
//        this.username = username;
    }

    @Override
    public void onJoinRoom(int roomId, String username) {
//        interactor.joinRoom(roomId);
//
        interactor.checkRoomExists(roomId, new RoomJoinInteractor.RoomCheckCallback() {
            @Override
            public void onRoomChecked(int roomId, boolean exists) {
                if (exists) {
                    System.out.println("Room " + roomId + " exists!");
                    interactor.joinRoom(roomId, username);
                } else {
                    System.out.println("Room " + roomId + " does not exist!");
                }
            }

            @Override
            public void onError(String message) {
                System.out.println("Error: " + message);
            }
        });
    }

    public void onCreateRoom(String username) {
        createRoomWithRetry(3, 1, username);
    }

    public void createRoomWithRetry(int maxAttempt, int currentAttempt, String username) {
        if (currentAttempt > maxAttempt) {
            System.out.println("Failed to create room after " + currentAttempt + " attempts");
        }
        int roomId = (int) (Math.random() * 9000) + 1000;
        interactor.checkRoomExists(roomId, new RoomJoinInteractor.RoomCheckCallback() {
            @Override
            public void onRoomChecked(int roomId, boolean exists) {
                if (!exists) {
                    interactor.createRoom(roomId, username);
                } else {
                    System.out.println("Room id exists, trying a different one...");
                    createRoomWithRetry(maxAttempt, currentAttempt + 1, username);
                }
            }

            @Override
            public void onError(String message) {
                System.out.println("error: " + message);
                System.out.println("retrying...");
                createRoomWithRetry(maxAttempt, currentAttempt + 1, username);
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