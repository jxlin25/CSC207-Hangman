package interface_adapter.Room;

import entity.game_session.GameState;
import interface_adapter.MakeGuess.MakeGuessViewModel; // Import MakeGuessViewModel
import interface_adapter.ViewManagerModel;
import use_case.Room.LobbyOutputBoundary;
import view.LobbyView;

public class LobbyPresenter implements LobbyOutputBoundary {
    private final LobbyViewModel lobbyViewModel;
    private final ViewManagerModel viewManagerModel;
    private final MakeGuessViewModel makeGuessViewModel; // Added MakeGuessViewModel

    public LobbyPresenter(LobbyViewModel lobbyViewModel, ViewManagerModel viewManagerModel, MakeGuessViewModel makeGuessViewModel) {
        this.lobbyViewModel = lobbyViewModel;
        this.viewManagerModel = viewManagerModel;
        this.makeGuessViewModel = makeGuessViewModel; // Initialize MakeGuessViewModel
    }

    @Override
    public void prepareGameView(GameState gameState) { // Keep GameState parameter for consistency if interactor passes it
        // The HangmanClient's onMessage handler will receive GAME_STATE_UPDATE
        // and is responsible for setting the MakeGuessViewModel's state and
        // switching the view. So, this presenter's role here is minimal.
        // We just ensure the ViewManager knows a game view should be active.
        viewManagerModel.setState(makeGuessViewModel.VIEW_NAME);
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void updateLobbyView(LobbyState lobbyState) {

    }

    @Override
    public void showError(String errorMessage) {}
}
