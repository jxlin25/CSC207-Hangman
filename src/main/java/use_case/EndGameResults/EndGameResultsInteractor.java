package use_case.EndGameResults;

import Constant.StatusConstant;
import data_access.InMemoryHangmanDataAccessObject;
import data_access.JsonStatsDataAccessObject;
import entity.HangmanGame;
import entity.Round;
import interface_adapter.EndGameResults.EndGameResultsState.RoundResult;
import use_case.Stats.StatsDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class EndGameResultsInteractor implements EndGameResultsInputBoundary {

    final EndGameResultsOutputBoundary EndGameResultsPresenter;
    final InMemoryHangmanDataAccessObject hangmanGameDAO;
    final JsonStatsDataAccessObject statsDAO;

    public EndGameResultsInteractor(EndGameResultsOutputBoundary EndGameResultsPresenter,
                                    InMemoryHangmanDataAccessObject hangmanGameDAO, JsonStatsDataAccessObject statsDAO) {
        this.EndGameResultsPresenter = EndGameResultsPresenter;
        this.hangmanGameDAO = hangmanGameDAO;
        this.statsDAO = statsDAO;
    }

    @Override
    public void execute(EndGameResultsInputData inputData) {
        HangmanGame game = hangmanGameDAO.getHangmanGame();

        // Get the LAST round to determine overall win/loss
        Round lastRound = game.getRounds().get(game.getRounds().size() - 1);
        String lastRoundStatus = lastRound.getStatus();

        // Determine overall game status based on the last round
        String finalStatus = "Game Over";

        // Build round-by-round results
        List<RoundResult> roundResults = new ArrayList<>();

        boolean gameWon = lastRoundStatus.equals(StatusConstant.WON);
        if (gameWon || lastRoundStatus.equals(StatusConstant.LOST)) {
            try {
                statsDAO.updateStats(gameWon);
                System.out.println("Stats recorded: " + (gameWon ? "WIN" : "LOSS"));
            } catch (Exception e) {
                System.err.println("Error recording stats: " + e.getMessage());
            }
        }

        for (int i = 0; i < game.getRounds().size(); i++) {
            Round round = game.getRounds().get(i);

            System.out.println("Round " + (i+1) + ":");
            System.out.println("  Status: " + round.getStatus());
            System.out.println("  Remaining Attempts: " + round.getAttempt());
            System.out.println("  Attempts Used: " + (hangmanGameDAO.getMaxAttempts() - round.getAttempt()));

            // Calculate attempts used for this round (6 - remaining)
            int attemptsUsed = hangmanGameDAO.getMaxAttempts() - round.getAttempt();

            // Get word
            String word = new String(round.getWordPuzzle().getLetters());

            // Get status
            String status = round.getStatus();
            String statusText = status.equals(Constant.StatusConstant.WON) ? "Won" : "Lost";

            // Create round result
            RoundResult result = new RoundResult(
                    i + 1,           // Round number (1-based)
                    word,            // The word
                    attemptsUsed,    // Attempts used in THIS round only
                    statusText       // Won or Lost
            );

            roundResults.add(result);
        }

        EndGameResultsPresenter.present(finalStatus, roundResults);
    }
}