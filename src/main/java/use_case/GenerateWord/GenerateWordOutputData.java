package use_case.GenerateWord;

import java.util.ArrayList;

/**
 * Output Data for the Generate Word Use Case.
 */

public class GenerateWordOutputData {

    private final ArrayList<String> words;

    public GenerateWordOutputData(ArrayList<String> words) {
        this.words = words;
    }

    public ArrayList<String> getWords() {
        return words;
    }
}
