package use_case.MakeGuess;

import static Constant.StatusConstant.*;
import entity.Guess;
import entity.Round;

public class MakeGuessInteractor implements MakeGuessInputBoundary {

    private final MakeGuessHangmanGameDataAccessInterface repo;
    private final MakeGuessOutputBoundary presenter;

    public MakeGuessInteractor(MakeGuessHangmanGameDataAccessInterface repo,
                               MakeGuessOutputBoundary presenter) {
        this.repo = repo;
        this.presenter = presenter;
    }

    @Override
    public void execute(MakeGuessInputData input) {
        String raw = input.getGuess();
        if (raw == null || raw.trim().isEmpty()) {
            emit("", "", repo.getCurrentRoundAttempt(),
                    "Please enter a letter.", null);
            return;
        }

        String normalized = raw.trim().toLowerCase();
        // Adjust if your Guess entity requires a different constructor
        Guess guess = new Guess(normalized);
        repo.addGuessToCurrentRound(guess);

        // If your DAO automatically reduces attempts only for incorrect guesses,
        // you can remove this line. Otherwise, you can decide when to call it.
        // repo.decreaseCurrentRoundAttempt();

        int attemptsLeft = repo.getCurrentRoundAttempt();
        Round round = repo.getCurrentRound();

        String maskedWord = "";      // fill from your domain when available
        String guessedLetters = "";  // fill from your domain when available
        String roundStatus = "IN_PROGRESS";
        String correctWordOnLoss = null;

        boolean won = false;                 // compute from your domain when available
        boolean lost = attemptsLeft <= 0;    // safe fallback

        if (won) {
            roundStatus = "WON";
            repo.setCurrentRoundWonAndStartNextRound();
        } else if (lost) {
            roundStatus = "LOST";
            repo.setCurrentRoundLostAndStartNextRound();
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
