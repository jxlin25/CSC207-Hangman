package interface_adapter.MakeGuess;

import entity.Guess;
import entity.WordPuzzle;

public class MakeGuessInteractor {
    private static final String GUESSING = "GUESSING";
    private static final String WON = "WON";
    private static final String LOST = "LOST";

    private final MakeGuessHangmanGameDataAccessInterface hangmanGameDAO;
    private final MakeGuessWordPuzzleDataAccessInterface wordPuzzleDAO;
    private final MakeGuessOutputBoundary presenter;

    public MakeGuessInteractor(MakeGuessHangmanGameDataAccessInterface hangmanGameDAO,
                               MakeGuessWordPuzzleDataAccessInterface wordPuzzleDAO,
                               MakeGuessOutputBoundary presenter) {
        this.hangmanGameDAO = hangmanGameDAO;
        this.wordPuzzleDAO = wordPuzzleDAO;
        this.presenter = presenter;
    }

    public void execute(MakeGuessInputData inputData) {
        Guess guess = inputData.getGuess();

        // decrement attempt and record guess in current round
        hangmanGameDAO.decreaseCurrentRoundAttempt();
        hangmanGameDAO.addGuessToCurrentRound(guess);

        boolean correct = wordPuzzleDAO.isGuessCorrect(guess);

        String status = GUESSING;
        boolean gameOver = false;

        if (correct) {
            wordPuzzleDAO.revealLetter(guess);
            if (wordPuzzleDAO.isPuzzleComplete()) {
                gameOver = hangmanGameDAO.setCurrentRoundWonAndStartNextRound();
                status = WON;
            }
        } else {
            int remaining = hangmanGameDAO.getCurrentRoundAttempt();
            if (remaining <= 0) {
                gameOver = hangmanGameDAO.setCurrentRoundLostAndStartNextRound();
                status = LOST;
            }
        }

        WordPuzzle puzzle = wordPuzzleDAO.getCurrentWordPuzzle();
        String masked = puzzle.getMaskedWord();
        String guessed = puzzle.getGuessedLettersString();
        int remaining = hangmanGameDAO.getCurrentRoundAttempt();
        String correctWordOnLoss = LOST.equals(status) ? puzzle.getSolution() : null;

        MakeGuessOutputData out = new MakeGuessOutputData(
                status, masked, remaining, guessed, correctWordOnLoss, gameOver
        );
        presenter.present(out);
    }
}
