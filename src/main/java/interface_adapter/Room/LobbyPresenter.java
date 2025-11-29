package interface_adapter.Room;

import entity.game_session.GameState;
import interface_adapter.ViewManagerModel;
import use_case.Room.LobbyOutputBoundary;
import view.LobbyView;

public class LobbyPresenter implements LobbyOutputBoundary {
    private final LobbyViewModel lobbyViewModel;
    private final ViewManagerModel viewManagerModel;

    public LobbyPresenter(LobbyViewModel lobbyViewModel, ViewManagerModel viewManagerModel) {
        this.lobbyViewModel = lobbyViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    public void prepareGameView(GameState gameState) {
        viewManagerModel.setViewName("MakeGuessView");
        viewManagerModel.firePropertyChange();
    }
}
