package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public abstract class ViewModel<T> {
    private final String viewName;
    private T state;
    protected final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    public void setState(T state) {
        this.state = state;
    }
    public T getState() { return state; }

    public void firePropertyChange() {
        support.firePropertyChange("state", null, state);
    }

    public String getViewName() {
        return viewName;
    }

    public void firePropertyChange(String propertyName) {
        this.support.firePropertyChange(propertyName, null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }
}