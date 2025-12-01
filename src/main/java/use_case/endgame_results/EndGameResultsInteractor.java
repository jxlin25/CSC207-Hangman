package use_case.endgame_results;

import data_access.InMemoryHangmanDataAccessObject;
import entity.HangmanGame;
import entity.Round;
import interface_adapter.endgame_results.EndGameResultsState.RoundResult;
import java.util.ArrayList;
import java.util.List;

public class EndGameResultsInteractor implements EndGameResultsInputBoundary {

    private final EndGameResultsOutputBoundary endGameResultsPresenter;
    private final EndGameResultsDataAccessInterface endGameResultsDataAccessObject;

    public EndGameResultsInteractor(EndGameResultsOutputBoundary EndGameResultsPresenter,
                                    InMemoryHangmanDataAccessObject endGameResultsDataAccessObject) {
        this.endGameResultsPresenter = EndGameResultsPresenter;
        this.endGameResultsDataAccessObject = endGameResultsDataAccessObject;
    }

    @Override
    public void execute(EndGameResultsInputData inputData) {
        final HangmanGame game = endGameResultsDataAccessObject.getHangmanGame();

        // Determine overall game status based on the last round
        final String finalStatus = "Game Over";

        // Build round-by-round results
        final List<RoundResult> roundResults = new ArrayList<>();

        for (int i = 0; i < game.getRounds().size(); i++) {
            final Round round = game.getRounds().get(i);

            System.out.println("Round " + (i + 1) + ":");
            System.out.println("  Status: " + round.getStatus());
            System.out.println("  Remaining Attempts: " + round.getAttempt());
            System.out.println("  Attempts Used: " + (endGameResultsDataAccessObject.getMaxAttempts() - round.getAttempt()));

            // Calculate attempts used for this round (6 - remaining)
            final int attemptsUsed = endGameResultsDataAccessObject.getMaxAttempts() - round.getAttempt();

            // Get word
            final String word = new String(round.getWordPuzzle().getLetters());

            // Get status
            final String status = round.getStatus();
            final String statusText = status.equals(constant.StatusConstant.WON) ? "Won" : "Lost";

            // Create round result
            final RoundResult result = new RoundResult(
                    i + 1,           // Round number (1-based)
                    word,            // The word
                    attemptsUsed,    // Attempts used in THIS round only
                    statusText       // Won or Lost
            );

            roundResults.add(result);
        }

        endGameResultsPresenter.present(finalStatus, roundResults);
    }
}