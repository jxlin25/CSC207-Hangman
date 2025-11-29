package use_case.MakeGuess;

import entity.Guess;

/**
 * The Interactor for the MakeGuess use case.
 */
public class MakeGuessInteractor implements MakeGuessInputBoundary {

    private final MakeGuessOutputBoundary presenter;
    private final HangmanGameDataAccessInterface hangmanGameDAO;

    public MakeGuessInteractor(MakeGuessOutputBoundary presenter, HangmanGameDataAccessInterface hangmanGameDAO) {
        this.presenter = presenter;
        this.hangmanGameDAO = hangmanGameDAO;
    }

    @Override
    public void execute(MakeGuessInputData inputData) {

        final Guess guess = inputData.getGuess();
        final int currentRoundNumber = this.hangmanGameDAO.getCurrentRoundNumber();
        int currentRemainingAttempts = this.hangmanGameDAO.getCurrentRoundAttempt();
        String currentMaskedWord = this.hangmanGameDAO.getCurrentMaskedWord();

        // add the guess to the current round
        this.hangmanGameDAO.addGuessToCurrentRound(guess);

        // Check if the letter in the guess exist in the word puzzle
        final boolean isGuessCorrect = hangmanGameDAO.isGuessCorrectToCurrentWordPuzzle(guess);

        String roundStatus = Constant.StatusConstant.GUESSING;
        boolean isGameOver = false;

        // If the guess is correct, reveal the correctly guessed letter and check if the puzzle is complete
        if (isGuessCorrect) {
            this.hangmanGameDAO.revealLetterInCurrentWordPuzzle(guess);
            currentMaskedWord = this.hangmanGameDAO.getCurrentMaskedWord();

            // If this guess leads to the completion of the puzzle,
            // mark the current round as WON
            if (this.hangmanGameDAO.isCurrentWordPuzzleComplete()) {

                roundStatus = Constant.StatusConstant.WON;

                if (this.hangmanGameDAO.isCurrentRoundTheLastRound()) {
                    isGameOver = true;
                    this.hangmanGameDAO.getCurrentRound().setWon();
                }
                else {
                    this.hangmanGameDAO.setCurrentRoundWonAndStartNextRound();
                }
            }
        }
        else {
            // Deduct 1 attempt if the guess is wrong
            this.hangmanGameDAO.decreaseCurrentRoundAttempt();
            currentRemainingAttempts = this.hangmanGameDAO.getCurrentRoundAttempt();

            // If the guess is the last guess and does not complete the puzzle,
            // mark the current round as LOST
            if (this.hangmanGameDAO.getCurrentRoundAttempt() == 0) {

                roundStatus = Constant.StatusConstant.LOST;

                if (this.hangmanGameDAO.isCurrentRoundTheLastRound()) {
                    isGameOver = true;
                    this.hangmanGameDAO.getCurrentRound().setLost();
                }
                else {
                    this.hangmanGameDAO.setCurrentRoundLostAndStartNextRound();
                }
            }
        }

//        // If the guess is correct, reveal the correctly guessed letter and check if the puzzle is complete
//        if (isGuessCorrect) {
//            this.hangmanGameDAO.revealLetterInCurrentWordPuzzle(guess);
//
//            // If this guess leads to the completion of the puzzle, mark the current round as WON and start next round
//            if (this.hangmanGameDAO.isCurrentWordPuzzleComplete()) {
//
//                roundStatus = Constant.StatusConstant.WON;
//
//                if (!this.hangmanGameDAO.setCurrentRoundWonAndStartNextRound()) {
//                    isGameOver = true;
//                }
//            }
//        }
//        else {
//            // Deduct 1 attempt if the guess is wrong
//            this.hangmanGameDAO.decreaseCurrentRoundAttempt();
//        }
//        final int currentRemainingAttempts = this.hangmanGameDAO.getCurrentRoundAttempt();
//
//        // If the guess is the last guess and does not complete the puzzle,
//        // mark the current round as LOST and start next round
//        if (!this.hangmanGameDAO.isCurrentWordPuzzleComplete() && this.hangmanGameDAO.getCurrentRoundAttempt() == 0) {
//            roundStatus = Constant.StatusConstant.LOST;
//
//            if (!this.hangmanGameDAO.setCurrentRoundLostAndStartNextRound()) {
//                isGameOver = true;
//            }
//        }

        final MakeGuessOutputData outputData = new MakeGuessOutputData(
                guess,
                isGuessCorrect,
                roundStatus,
                isGameOver,
                currentRemainingAttempts,
                currentRoundNumber,
                currentMaskedWord);

        presenter.updateView(outputData);
    }
}
