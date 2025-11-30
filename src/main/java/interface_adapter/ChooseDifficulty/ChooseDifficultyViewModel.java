package interface_adapter.ChooseDifficulty;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ChooseDifficultyViewModel {

    public static final String VIEW_NAME = "Choose Difficulty";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private ChooseDifficultyState state = new ChooseDifficultyState();

    public String getViewName() {
        return VIEW_NAME;
    }

    public ChooseDifficultyState getState() {
        return state;
    }

    public void setState(ChooseDifficultyState state) {
        this.state = state;
    }

    public void firePropertyChange() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}

