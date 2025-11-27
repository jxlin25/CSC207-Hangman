package interface_adapter.Room;

import entity.game_session.LobbyState;
import interface_adapter.ViewModel;
import java.util.List;
import entity.Player;

public class LobbyViewModel extends ViewModel<LobbyState> {

    public static final String VIEW_NAME = "lobby view";

    public LobbyViewModel() {
        super(VIEW_NAME);
        setState(new LobbyState());
    }
}
