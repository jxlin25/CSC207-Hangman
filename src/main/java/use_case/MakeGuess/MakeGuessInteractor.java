package use_case.MakeGuess;

import entity.Guess;

/**
 * The Interactor for the MakeGuess use case.
 */
public class MakeGuessInteractor implements MakeGuessInputBoundary {

    private final MakeGuessOutputBoundary presenter;
    private final MakeGuessHangmanGameDataAccessInterface hangmanGameDAO;

    public MakeGuessInteractor(MakeGuessOutputBoundary presenter,
                               MakeGuessHangmanGameDataAccessInterface hangmanGameDAO) {
        this.presenter = presenter;
        this.hangmanGameDAO = hangmanGameDAO;
    }

    @Override
    public void execute(MakeGuessInputData inputData) {

        final Guess guess = inputData.getGuess();

        // NEW: read maxAttempts (initial attempts) from DAO, which was set by difficulty
        int maxAttempts = this.hangmanGameDAO.getInitialAttemptsForGame();
        if (maxAttempts <= 0) { // safe fallback, should not usually happen
            maxAttempts = 6;
        }

        // NEW: check current attempts BEFORE changing anything
        int currentAttempts = this.hangmanGameDAO.getCurrentRoundAttempt();
        if (currentAttempts <= 0) {
            // No attempts left: do not modify the game, just report that no more guesses are allowed
            String currentWord = this.hangmanGameDAO.getCurrentWord();

            final MakeGuessOutputData outputData = new MakeGuessOutputData(
                    guess,
                    false,
                    Constant.StatusConstant.LOST, // or GUESSING, depending on semantics
                    true,                         // treat as game/round over from guessing perspective
                    0,
                    this.hangmanGameDAO.getCurrentRoundNumber(),
                    this.hangmanGameDAO.getMaskedWord(),
                    currentWord,
                    maxAttempts                  // NEW: pass through
            );

            presenter.updateView(outputData);
            return;
        }

        // add the guess to the current round
        this.hangmanGameDAO.addGuessToCurrentRound(guess);

        // Check if the letter in the guess exist in the word puzzle
        final boolean isGuessCorrect = hangmanGameDAO.isGuessCorrectToCurrentWordPuzzle(guess);

        String roundStatus = Constant.StatusConstant.GUESSING;
        boolean isGameOver = false;

        // Word of the round that just ended (for win/lose dialogs)
        String endedRoundWord = null;

        // If the guess is correct, reveal the correctly guessed letter and check if the puzzle is complete
        if (isGuessCorrect) {
            this.hangmanGameDAO.revealLetterInCurrentWordPuzzle(guess);

            // If this guess leads to the completion of the puzzle, mark the current round as WON and start next round
            if (this.hangmanGameDAO.isCurrentWordPuzzleComplete()) {

                // Capture current round's word BEFORE moving to the next round
                endedRoundWord = hangmanGameDAO.getCurrentWord();
                roundStatus = Constant.StatusConstant.WON;

                if (!this.hangmanGameDAO.setCurrentRoundWonAndStartNextRound()) {
                    isGameOver = true;
                }
            }
        }
        else {
            // Deduct 1 attempt if the guess is wrong
            this.hangmanGameDAO.decreaseCurrentRoundAttempt();
        }

        // CHANGED: clamp remainingAttempts to never go below 0
        int remainingAttempts = this.hangmanGameDAO.getCurrentRoundAttempt();
        if (remainingAttempts < 0) {
            remainingAttempts = 0;
        }

        // If the guess is the last guess and does not complete the puzzle,
        // mark the current round as LOST and start next round
        if (!this.hangmanGameDAO.isCurrentWordPuzzleComplete()
                && remainingAttempts == 0) { // CHANGED: use remainingAttempts
            if (endedRoundWord == null) {
                // capture word BEFORE switching to next round
                endedRoundWord = hangmanGameDAO.getCurrentWord();
            }

            roundStatus = Constant.StatusConstant.LOST;

            if (!this.hangmanGameDAO.setCurrentRoundLostAndStartNextRound()) {
                isGameOver = true;
            }
        }

        // If still guessing, we can still pass current word (optional).
        if (endedRoundWord == null) {
            endedRoundWord = hangmanGameDAO.getCurrentWord();
        }

        final MakeGuessOutputData outputData = new MakeGuessOutputData(
                guess,
                isGuessCorrect,
                roundStatus,
                isGameOver,
                remainingAttempts,
                this.hangmanGameDAO.getCurrentRoundNumber(),
                this.hangmanGameDAO.getMaskedWord(),
                endedRoundWord,
                maxAttempts               // CHANGED: now a real variable we defined above
        );

        presenter.updateView(outputData);
    }
}