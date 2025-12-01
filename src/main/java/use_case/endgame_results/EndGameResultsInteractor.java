package use_case.endgame_results;

import java.util.ArrayList;
import java.util.List;

import data_access.InMemoryHangmanDataAccessObject;
import entity.HangmanGame;
import entity.Round;
import interface_adapter.endgame_results.EndGameResultsState.RoundResult;

/**
 * The Interactor for the EndGameResults use case.
 */
public class EndGameResultsInteractor implements EndGameResultsInputBoundary {

    private final EndGameResultsOutputBoundary endGameResultsPresenter;
    private final InMemoryHangmanDataAccessObject hangmanGameDAO;

    public EndGameResultsInteractor(EndGameResultsOutputBoundary endGameResultsPresenter,
                                    InMemoryHangmanDataAccessObject hangmanGameDAO) {
        this.endGameResultsPresenter = endGameResultsPresenter;
        this.hangmanGameDAO = hangmanGameDAO;
    }

    @Override
    public void execute(EndGameResultsInputData inputData) {
        final HangmanGame game = hangmanGameDAO.getHangmanGame();

        // Determine overall game status based on the last round
        final String finalStatus = "Game Over";

        // Build round-by-round results
        final List<RoundResult> roundResults = new ArrayList<>();

        // Get the max attempts from the game (which respects difficulty level)
        final int maxAttempts = hangmanGameDAO.getMaxAttempts();

        for (int i = 0; i < game.getRounds().size(); i++) {
            final Round round = game.getRounds().get(i);

            System.out.println("Round " + (i + 1) + ":");
            System.out.println("  Status: " + round.getStatus());
            System.out.println("  Remaining Attempts: " + round.getAttempt());
            System.out.println("  Max Attempts: " + maxAttempts);
            System.out.println("  Attempts Used: " + (maxAttempts - round.getAttempt()));

            // Calculate attempts used for this round (maxAttempts - remaining)
            final int attemptsUsed = maxAttempts - round.getAttempt();

            // Get word
            final String word = new String(round.getWordPuzzle().getLetters());

            // Get status
            final String status = round.getStatus();
            final String statusText;
            if (status.equals(constant.StatusConstant.WON)) {
                statusText = "Won";
            }
            else {
                statusText = "Lost";
            }

            // Create round result
            final RoundResult result = new RoundResult(
                    i + 1,
                    word,
                    attemptsUsed,
                    statusText
            );

            roundResults.add(result);
        }

        endGameResultsPresenter.present(finalStatus, roundResults);
    }
}