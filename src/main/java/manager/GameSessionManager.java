package manager;
import entity.Player;
import interface_adapter.GameSession.GameSessionController;
import entity.game_session.GameState;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class GameSessionManager {
    private static GameSessionManager instance;
    private final Map<Integer, GameSessionController> sessions = new HashMap<>();

    public void createSession(int roomId, List<Player> players) {
        GameState gameState = new GameState(roomId, players);
        GameSessionController controller = new GameSessionController(gameState);
        sessions.put(roomId, controller);
//        return controller;
    }

    public static GameSessionManager getInstance() {
        if (instance == null) {
            instance = new GameSessionManager();
        }
        return instance;
    }

}


