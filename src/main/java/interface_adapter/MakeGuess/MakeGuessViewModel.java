package interface_adapter.MakeGuess;

import entity.game_session.GameState; // Import GameState
import interface_adapter.ViewModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MakeGuessViewModel extends ViewModel<MakeGuessState> {
    public static final String VIEW_NAME = "Make Guess";

    public MakeGuessViewModel() {
        super(VIEW_NAME);
        setState(new MakeGuessState());
    }
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        // This notifies the View that the state has changed
        support.firePropertyChange("state", null, this.getState());
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    // New method to set state from GameState
    public void setStateFromGameState(GameState gameState, String currentClientId) {
        MakeGuessState state = this.getState(); // Get current mutable state

        state.setMaskedWord(gameState.getRevealedWord());
        state.setCurrentRoundNumber(gameState.getCurrentRound());
        state.setRemainingAttempts(gameState.getMaxIncorrectGuesses() - gameState.getIncorrectGuessesCount());
        state.setGameOver(gameState.getCurrentPhase() == entity.game_session.GamePhase.GAME_OVER);
        state.setRoundStatus(gameState.getCurrentPhase().toString()); // Convert GamePhase enum to String
        state.setGuessedLetters(gameState.getGuessedLetters());

        // Multiplayer specific fields
        state.setCurrentWordSetterId(gameState.getCurrentWordSetterId());
        state.setCurrentGuesserId(gameState.getCurrentGuesserId());
        state.setSecretWord(gameState.getSecretWord());
        state.setMaxIncorrectGuesses(gameState.getMaxIncorrectGuesses());
        state.setCurrentClientId(currentClientId); // Set the client ID for this specific client
        state.setMultiplayerGame(true); // Indicate it's a multiplayer game

        // We do not call firePropertyChanged here, it should be called after all state updates
        // from the client's handleGameStateUpdate or presenter's updateView.
    }
}
