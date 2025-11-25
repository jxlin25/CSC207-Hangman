package entity.game_session;
import entity.Player;
import java.util.List;
import java.util.Map;

public class LobbyState {
    private String roomId;
    private List<Player> players;
    private Map<String, Boolean> playerReadyStatus;
    private String hostPlayerId;
    private boolean canStartGame;



}