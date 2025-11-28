package use_case.Hint;

/**
 * The input data for the Hint Use Case.
 */

public class HintInputData {
    private String word;

    public HintInputData(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}
