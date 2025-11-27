package interface_adapter.Room;

import interface_adapter.ViewModel;
import interface_adapter.Room.RoomJoinState;

public class RoomJoinViewModel extends ViewModel<RoomJoinState> {

    public static final String TITLE_LABEL = "Join Room";
    public static final String USERNAME_LABEL = "Enter username";
    public static final String ROOM_ID_LABEL = "Enter room ID";
    public static final String JOIN_BUTTON_LABEL = "Join Room";
    public static final String CREATE_BUTTON_LABEL = "Create New Room";

    public RoomJoinViewModel() {
        super("room join");  // Calls parent constructor with view name
        setState(new RoomJoinState());  // Initializes with empty state
    }
}
