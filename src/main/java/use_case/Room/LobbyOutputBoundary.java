package use_case.Room;

import entity.game_session.GameState;
import entity.game_session.LobbyState;

public interface LobbyOutputBoundary {
    void prepareGameView(GameState gameState);
    void updateLobbyView(LobbyState lobbyState);
    void showError(String errorMessage);
}
