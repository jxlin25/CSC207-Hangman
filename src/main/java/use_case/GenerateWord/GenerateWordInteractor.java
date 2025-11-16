package use_case.GenerateWord;

import entity.WordPuzzle;

public class GenerateWordInteractor implements GenerateWordInputBoundary {

    private final WordPuzzleDataAccessInterface dataAccess;
    private final GenerateWordOutputBoundary presenter;

    public GenerateWordInteractor(WordPuzzleDataAccessInterface dataAccess,
                                  GenerateWordOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        for (int attempt = 0; attempt < 10; attempt++) {

            String word = dataAccess.getRandomWord();

            if (dataAccess.isValidWord(word)) {
                WordPuzzle puzzle = new WordPuzzle(word.toCharArray());
                presenter.prepareSuccessView(new GenerateWordOutputData(puzzle));
                return;
            }
        }

        presenter.prepareFailView("Failed to generate a valid word after 10 attempts.");
    }
}