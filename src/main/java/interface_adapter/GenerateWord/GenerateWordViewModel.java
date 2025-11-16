package interface_adapter.GenerateWord;

import entity.WordPuzzle;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GenerateWordViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private WordPuzzle puzzle;
    private String error;

    public void setPuzzle(WordPuzzle puzzle) {
        this.puzzle = puzzle;
        support.firePropertyChange("puzzle", null, puzzle);
    }

    public void setError(String error) {
        this.error = error;
        support.firePropertyChange("error", null, error);
    }

    public WordPuzzle getPuzzle() { return puzzle; }
    public String getError() { return error; }

    public void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}