package use_case.Room;

import interface_adapter.GameSession.GameSessionController;
import interface_adapter.Room.LobbyState;
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
        boolean success = dataAccess.sendStartGameRequest(roomId);

        if (!success) {
            presenter.showError("Failed to send start game request to server.");
        }
    }
}
