package use_case.Room;

import entity.Player;
import entity.game_session.GameState;
import entity.game_session.LobbyState;
import interface_adapter.GameSession.GameSessionController;
import manager.GameSessionManager;

public class LobbyInteractor implements LobbyInputBoundary{
    private final LobbyOutputBoundary presenter;
    private final LobbyDataAccessInterface dataAccess;
    private final GameSessionManager sessionManager;

    public LobbyInteractor(LobbyOutputBoundary presenter,
                           LobbyDataAccessInterface dataAccess,
                           GameSessionManager sessionManager) {
        this.presenter = presenter;
        this.dataAccess = dataAccess;
        this.sessionManager = sessionManager;
    }


    public void startGame(int roomId) {
        LobbyState lobbyState = dataAccess.getLobbyState(roomId);
//
//        if (!canStartGame(lobbyState)) {
//            presenter.showError("Cannot start game - need 2 ready players");
//            return;
//        }
//        GameSessionController gameSession = sessionManager.createSession(roomId, lobbyState.getPlayers());
//
//        presenter.prepareGameView(gameSession.getGameState());
    }
}
