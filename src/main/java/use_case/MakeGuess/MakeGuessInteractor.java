package use_case.MakeGuess;

import entity.Guess;

public class MakeGuessInteractor implements MakeGuessInputBoundary {

    private final MakeGuessOutputBoundary presenter;
    private final MakeGuessWordPuzzleDataAccessInterface wordPuzzleDAO;

    public MakeGuessInteractor(MakeGuessOutputBoundary presenter,  MakeGuessWordPuzzleDataAccessInterface wordPuzzleDAO) {
        this.presenter = presenter;
        this.wordPuzzleDAO = wordPuzzleDAO;
    }

    @Override
    public void execute(MakeGuessInputData inputData) {

        Guess guess = inputData.getGuess();

        // Check if the letter in the guess exist in the word puzzle
        boolean isGuessCorrect = wordPuzzleDAO.isGuessCorrect(guess);

        boolean isPuzzleComplete = false;

        // If the guess is correct, reveal the correctly guessed letter and check if the puzzle is complete
        if (isGuessCorrect) {
            this.wordPuzzleDAO.revealLetter(guess);
            isPuzzleComplete = this.wordPuzzleDAO.isPuzzleComplete();
        }

        MakeGuessOutputData outputData =
                new MakeGuessOutputData(guess, isGuessCorrect, isPuzzleComplete);

        //TODO: implement the presenter
        presenter.updateView(outputData);
    }
}
