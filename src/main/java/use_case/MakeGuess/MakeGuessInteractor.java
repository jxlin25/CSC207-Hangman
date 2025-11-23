package use_case.MakeGuess;

import entity.Guess;

/**
 * The Interactor for the MakeGuess use case.
 */
public class MakeGuessInteractor implements MakeGuessInputBoundary {

    private final MakeGuessOutputBoundary presenter;
    private final MakeGuessHangmanGameDataAccessInterface hangmanGameDAO;

    public MakeGuessInteractor(MakeGuessOutputBoundary presenter, MakeGuessHangmanGameDataAccessInterface hangmanGameDAO) {
        this.presenter = presenter;
        this.hangmanGameDAO = hangmanGameDAO;
    }

    @Override
    public void execute(MakeGuessInputData inputData) {

        final Guess guess = inputData.getGuess();

        // add the guess to the current round
        this.hangmanGameDAO.addGuessToCurrentRound(guess);

        // Check if the letter in the guess exist in the word puzzle
        final boolean isGuessCorrect = hangmanGameDAO.isGuessCorrectToCurrentWordPuzzle(guess);

        String roundStatus = constant.StatusConstant.GUESSING;
        boolean isGameOver = false;

        // If the guess is correct, reveal the correctly guessed letter and check if the puzzle is complete
        if (isGuessCorrect) {
            this.hangmanGameDAO.revealLetterInCurrentWordPuzzle(guess);

            // If this guess leads to the completion of the puzzle, mark the current round as WON and start next round
            if (this.hangmanGameDAO.isCurrentWordPuzzleComplete()) {

                roundStatus = constant.StatusConstant.WON;

                if (!this.hangmanGameDAO.setCurrentRoundWonAndStartNextRound()) {
                    isGameOver = true;
                }
            }
        }
        else {
            // Deduct 1 attempt if the guess is wrong
            this.hangmanGameDAO.decreaseCurrentRoundAttempt();
        }
        final int remainingAttempts = this.hangmanGameDAO.getCurrentRoundAttempt();

        // If the guess is the last guess and does not complete the puzzle,
        // mark the current round as LOST and start next round
        if (!this.hangmanGameDAO.isCurrentWordPuzzleComplete() && this.hangmanGameDAO.getCurrentRoundAttempt() == 0) {
            roundStatus = constant.StatusConstant.LOST;

            if (!this.hangmanGameDAO.setCurrentRoundLostAndStartNextRound()) {
                isGameOver = true;
            }
        }

        final MakeGuessOutputData outputData = new MakeGuessOutputData(
                guess,
                isGuessCorrect,
                roundStatus,
                isGameOver,
                remainingAttempts,
                this.hangmanGameDAO.getCurrentRoundNumber(),
                this.hangmanGameDAO.getMaskedWord());

        presenter.updateView(outputData);
    }
}
