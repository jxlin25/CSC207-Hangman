package interface_adapter.GenerateWord;

import java.util.List;

/**
 * The state for the Generate Word View Model.
 */

public class GenerateWordState {
    private List<String> words;
    private String error;

    public List<String> getWords() {
        return words;
    }

    public String getError() {
        return error;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public void setError(String error) {
        this.error = error;
    }
}
