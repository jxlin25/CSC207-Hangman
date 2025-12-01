package use_case.make_guess;

import entity.Guess;

/**
 * The Interactor for the MakeGuess use case.
 */
public class MakeGuessInteractor implements MakeGuessInputBoundary {

    private final MakeGuessOutputBoundary presenter;
    private final MakeGuessDataAccessInterface dataAccessObject;

    public MakeGuessInteractor(MakeGuessOutputBoundary presenter,
                               MakeGuessDataAccessInterface dataAccessObject) {
        this.presenter = presenter;
        this.dataAccessObject = dataAccessObject;
    }

    @Override
    public void execute(MakeGuessInputData inputData) {

        final Guess guess = inputData.getGuess();
        final int currentRoundNumber = this.dataAccessObject.getCurrentRoundNumber();
        int currentRemainingAttempts = this.dataAccessObject.getCurrentRoundAttempt();
        String currentMaskedWord = this.dataAccessObject.getCurrentMaskedWord();
        String roundStatus = constant.StatusConstant.GUESSING;
        boolean isGameOver = false;
        final String fullWord = this.dataAccessObject.getCurrentWord();
        // Check if the letter in the guess exist in the word puzzle
        final boolean isGuessCorrect = dataAccessObject.isGuessCorrectToCurrentWordPuzzle(guess);

        // Add the guess to the current round
        this.dataAccessObject.addGuessToCurrentRound(guess);

        // If the guess is correct, reveal the correctly guessed letter and check if the puzzle is complete
        if (isGuessCorrect) {
            this.dataAccessObject.revealLetterInCurrentWordPuzzle(guess);
            currentMaskedWord = this.dataAccessObject.getCurrentMaskedWord();

            // If this guess leads to the completion of the puzzle,
            // mark the current round as Won
            if (this.dataAccessObject.isCurrentWordPuzzleComplete()) {

                roundStatus = constant.StatusConstant.WON;

                // If this is the last round, mark the game as over
                if (this.dataAccessObject.isCurrentRoundTheLastRound()) {
                    isGameOver = true;
                    this.dataAccessObject.getCurrentRound().setWon();
                }
                // If not, move to the next round
                else {
                    this.dataAccessObject.setCurrentRoundWonAndStartNextRound();
                }
            }
        }
        else {
            // Deduct 1 attempt if the guess is wrong
            this.dataAccessObject.decreaseCurrentRoundAttempt();
            currentRemainingAttempts = this.dataAccessObject.getCurrentRoundAttempt();

            // If the guess is the last guess and does not complete the puzzle,
            // mark the current round as Lost
            if (this.dataAccessObject.getCurrentRoundAttempt() == 0) {

                roundStatus = constant.StatusConstant.LOST;

                // If this is the last round, mark the game as over
                if (this.dataAccessObject.isCurrentRoundTheLastRound()) {
                    isGameOver = true;
                    this.dataAccessObject.getCurrentRound().setLost();
                }
                // If not, move to the next round
                else {
                    this.dataAccessObject.setCurrentRoundLostAndStartNextRound();
                }
            }
        }

        final MakeGuessOutputData outputData = new MakeGuessOutputData(
                guess,
                isGuessCorrect,
                roundStatus,
                isGameOver,
                currentRemainingAttempts,
                currentRoundNumber,
                currentMaskedWord,
                fullWord);

        presenter.updateView(outputData);
    }
}
