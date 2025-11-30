package use_case.GenerateWord;

import java.util.ArrayList;

/**
 * Output Data for the Generate Word Use Case.
 */

public class GenerateWordOutputData {

    private final ArrayList<String> words;
    private final int attempts;

    public GenerateWordOutputData(ArrayList<String> words, int attempts) {
        this.words = words;
        this.attempts = attempts;
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public int getAttempts() {
        return attempts;
    }
}
