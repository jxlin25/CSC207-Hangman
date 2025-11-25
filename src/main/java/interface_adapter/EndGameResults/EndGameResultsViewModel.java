package interface_adapter.EndGameResults;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class EndGameResultsViewModel extends ViewModel<EndGameResultsState> {

    public final String VIEW_NAME = "ResultsView";
    private EndGameResultsState state = new EndGameResultsState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public EndGameResultsViewModel() {
        super("ResultsView");
    }

    public EndGameResultsState getState() {
        return state;
    }

    public void setState(EndGameResultsState state) {
        this.state = state;
    }

    @Override
    public void firePropertyChange() {
        support.firePropertyChange("state", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}