package interface_adapter.MakeGuess;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/** The View Model for the MakeGuess use case.
 */
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
}
