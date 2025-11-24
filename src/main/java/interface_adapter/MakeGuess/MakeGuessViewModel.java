package interface_adapter.MakeGuess;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MakeGuessViewModel extends ViewModel<MakeGuessState> {
    public static final String VIEW_NAME = "Make Guess";
    public static final String GUESS_BUTTON_LABEL = "Guess";
    public static final String STATE_PROPERTY = "state";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private final MakeGuessState state = new MakeGuessState();

    public MakeGuessState getState() {
        return state;
    }
    public void setMaskedWord(String masked) { state.setMaskedWord(masked); fire(); }
    public void setRemainingAttempts(int attempts) { state.setRemainingAttempts(attempts); fire(); }
    public void setRoundStatus(String status) { state.setRoundStatus(status); fire(); }

    public MakeGuessViewModel(String viewName) {
        super(viewName);
        setState(new MakeGuessState());
    }

    public void firePropertyChanged() {
        // This notifies the View that the state has changed
        support.firePropertyChange("state", null, this.getState());
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    private void fire() {
        support.firePropertyChange(STATE_PROPERTY, null, state);
    }
}
