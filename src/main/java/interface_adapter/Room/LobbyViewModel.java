package interface_adapter.Room;

import interface_adapter.ViewModel;

public class LobbyViewModel extends ViewModel<LobbyState> {

    public static final String VIEW_NAME = "lobby view";

    public LobbyViewModel() {
        super(VIEW_NAME);
        setState(new LobbyState());
    }
}
