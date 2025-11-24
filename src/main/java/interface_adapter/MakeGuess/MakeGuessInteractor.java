package interface_adapter.MakeGuess;

import entity.Guess;
import entity.WordPuzzle;
import java.util.ArrayList;
import use_case.MakeGuess.MakeGuessOutputBoundary;

public class MakeGuessInteractor implements lsMakeGuessInputBoundary {

    private final MakeGuessHangmanGameDataAccessInterface hangmanDAO;
    private final MakeGuessOutputBoundary presenter;

    public MakeGuessInteractor(MakeGuessHangmanGameDataAccessInterface hangmanDAO,
                               MakeGuessOutputBoundary presenter) {
        this.hangmanDAO = hangmanDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(MakeGuessInputData input) {
        String raw = input.getGuess();
        if (raw == null || raw.trim().isEmpty()) {
            emit("", "", hangmanDAO.getCurrentRoundAttempt(),
                    "Please enter a letter.", null);
            return;
        }

        char normalized = raw.trim().toLowerCase();
        Guess guess = new Guess(normalized);
        hangmanDAO.addGuessToCurrentRound(guess);


        hangmanDAO.decreaseCurrentRoundAttempt();

        int attemptsLeft = hangmanDAO.getCurrentRoundAttempt();
        Round round = hangmanDAO.getCurrentRound();

        String maskedWord = "";      // fill from your domain when available
        String guessedLetters = "";  // fill from your domain when available
        String roundStatus = "IN_PROGRESS";
        String correctWordOnLoss = null;

        boolean won = false;                 // compute from your domain when available
        boolean lost = attemptsLeft <= 0;    // safe fallback

        if (won) {
            roundStatus = "WON";
            hangmanDAO.setCurrentRoundWonAndStartNextRound();
        } else if (lost) {
            roundStatus = "LOST";
            hangmanDAO.setCurrentRoundLostAndStartNextRound();
            correctWordOnLoss = ""; // set real solution when domain provides it
        }

        emit(maskedWord, guessedLetters, attemptsLeft, roundStatus, correctWordOnLoss);
    }

    private void emit(String maskedWord,
                      String guessedLetters,
                      int remainingAttempts,
                      String roundStatus,
                      String correctWordOnLoss) {
        presenter.updateView(new MakeGuessOutputData(
                maskedWord, guessedLetters, remainingAttempts, roundStatus, correctWordOnLoss));
    }
}
