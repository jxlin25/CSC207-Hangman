package use_case.EndGameResults;

import data_access.InMemoryHangmanDataAccessObject;
import entity.HangmanGame;
import entity.Round;

public class EndGameResultsInteractor implements EndGameResultsInputBoundary {

    final EndGameResultsOutputBoundary EndGameResultsPresenter;
    final InMemoryHangmanDataAccessObject hangmanGameDAO;

    public EndGameResultsInteractor(EndGameResultsOutputBoundary EndGameResultsPresenter,
                                    InMemoryHangmanDataAccessObject hangmanGameDAO) {
        this.EndGameResultsPresenter = EndGameResultsPresenter;
        this.hangmanGameDAO = hangmanGameDAO;
    }

    @Override
    public void execute(EndGameResultsInputData inputData) {
        HangmanGame game = hangmanGameDAO.getHangmanGame();

        // Get the LAST round to determine overall win/loss
        Round lastRound = game.getRounds().get(game.getRounds().size() - 1);
        String lastRoundStatus = lastRound.getStatus();

        // Determine overall game status based on the last round
        String finalStatus;
        if (lastRoundStatus.equals(Constant.StatusConstant.WON)) {
            finalStatus = "Victory! 🎉";
        } else if (lastRoundStatus.equals(Constant.StatusConstant.LOST)) {
            finalStatus = "Defeat 😢";
        } else {
            finalStatus = "Game Ended";
        }

        // Build detailed round-by-round results
        int totalAttemptsTaken = 0;
        StringBuilder roundDetails = new StringBuilder();

        for (int i = 0; i < game.getRounds().size(); i++) {
            Round round = game.getRounds().get(i);

            // Calculate attempts used for this round
            int attemptsUsed = 6 - round.getAttempt();
            totalAttemptsTaken += attemptsUsed;

            // Get word
            String word = new String(round.getWordPuzzle().getLetters());

            // Get status
            String status = round.getStatus();
            String statusText = status.equals(Constant.StatusConstant.WON) ? "Won ✓" : "Lost ✗";

            // Format: "Round 1: Apple - Won ✓"
            roundDetails.append("Round ").append(i + 1).append(": ")
                    .append(word).append(" - ")
                    .append(statusText);

            // Add newline if not the last round
            if (i < game.getRounds().size() - 1) {
                roundDetails.append("\n");
            }
        }

        String finalWord = roundDetails.toString();

        EndGameResultsPresenter.present(finalStatus, finalWord, totalAttemptsTaken);
    }
}