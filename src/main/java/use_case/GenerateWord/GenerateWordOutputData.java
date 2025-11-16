package use_case.GenerateWord;

import entity.WordPuzzle;

public class GenerateWordOutputData {

    private final WordPuzzle puzzle;

    public GenerateWordOutputData(WordPuzzle puzzle) {
        this.puzzle = puzzle;
    }

    public WordPuzzle getPuzzle() {
        return puzzle;
    }
}
