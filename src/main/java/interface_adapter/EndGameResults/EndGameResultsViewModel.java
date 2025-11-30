package interface_adapter.EndGameResults;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import interface_adapter.ViewModel;

public class EndGameResultsViewModel extends ViewModel<EndGameResultsState> {

    private final String viewName = "ResultsView";
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
