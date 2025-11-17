package use_case.MakeGuess;

import entity.Guess;

import static Constant.StatusConstant.*;

public class MakeGuessInteractor implements MakeGuessInputBoundary {

    private final MakeGuessOutputBoundary presenter;
    private final MakeGuessWordPuzzleDataAccessInterface wordPuzzleDAO;
    private final MakeGuessHangmanGameDataAccessInterface hangmanGameDAO;

    public MakeGuessInteractor(MakeGuessOutputBoundary presenter, MakeGuessWordPuzzleDataAccessInterface wordPuzzleDAO, MakeGuessHangmanGameDataAccessInterface hangmanGameDAO) {
        this.presenter = presenter;
        this.wordPuzzleDAO = wordPuzzleDAO;
        this.hangmanGameDAO = hangmanGameDAO;
    }

    @Override
    public void execute(MakeGuessInputData inputData) {

        Guess guess = inputData.getGuess();

        // Deduct 1 attempt from the play in the current round
        this.hangmanGameDAO.decreaseCurrentRoundAttempt();
        // add the guess to the current round
        this.hangmanGameDAO.addGuessToCurrentRound(guess);

        // Check if the letter in the guess exist in the word puzzle
        boolean isGuessCorrect = wordPuzzleDAO.isGuessCorrect(guess);

        String roundStatus = GUESSING;
        boolean isGameOver = false;

        // If the guess is correct, reveal the correctly guessed letter and check if the puzzle is complete
        if (isGuessCorrect) {
            this.wordPuzzleDAO.revealLetter(guess);

            // If this guess leads to the completion of the puzzle, mark the current round as WON and start next round
            if (this.wordPuzzleDAO.isPuzzleComplete()){
                roundStatus = WON;

                if (!this.hangmanGameDAO.setCurrentRoundWonAndStartNextRound()) {
                    isGameOver = true;
                }
            }
        }

        // If the guess is the last guess and it does not complete the puzzle, mark the current round as LOST and start next round
        if (!this.wordPuzzleDAO.isPuzzleComplete() && this.hangmanGameDAO.getCurrentRoundAttempt() == 0) {
            roundStatus = LOST;

            if (!this.hangmanGameDAO.setCurrentRoundLostAndStartNextRound()) {
                isGameOver = true;
            }
        }

        MakeGuessOutputData outputData =
                new MakeGuessOutputData(guess, isGuessCorrect, roundStatus, isGameOver);

        presenter.updateView(outputData);
    }
}
