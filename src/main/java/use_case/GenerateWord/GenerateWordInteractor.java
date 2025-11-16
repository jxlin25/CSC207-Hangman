package use_case.GenerateWord;

import entity.WordPuzzle;

public class GenerateWordInteractor implements GenerateWordInputBoundary {

    private final WordPuzzleDataAccessInterface wordPuzzleDataAccessInterface;
    private final GenerateWordOutputBoundary generateWordOutputBoundary;

    public GenerateWordInteractor(WordPuzzleDataAccessInterface wordPuzzleDataAccessInterface,
                                  GenerateWordOutputBoundary generateWordOutputBoundary) {
        this.wordPuzzleDataAccessInterface = wordPuzzleDataAccessInterface;
        this.generateWordOutputBoundary = generateWordOutputBoundary;
    }

    @Override
    public void execute() {
        String word = wordPuzzleDataAccessInterface.getRandomWord();
        if (wordPuzzleDataAccessInterface.isValidWord(word)) {
            WordPuzzle puzzle = new WordPuzzle(word.toCharArray());
            generateWordOutputBoundary.prepareSuccessView(new GenerateWordOutputData(puzzle));
            return;
        }
        //TODO here must be pass because isValidWord only return true now.
        return;
    }
}