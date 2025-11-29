package interface_adapter.Room;

import interface_adapter.ViewModel;

public class RoomJoinViewModel extends ViewModel<RoomJoinState> {

    public static final String VIEW_NAME = "room join view";

    public RoomJoinViewModel() {
        super(VIEW_NAME);
        setState(new RoomJoinState());
    }

}
