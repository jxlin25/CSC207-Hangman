package interface_adapter.SubmitWord;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SubmitWordViewModel extends ViewModel<SubmitWordState> {
    public static final String VIEW_NAME = "submit_word";

    public SubmitWordViewModel() {
        super(VIEW_NAME);
        setState(new SubmitWordState());
    }
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChange() {
        support.firePropertyChange("state", null, this.getState());
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
