package use_case.endgame_results;

import data_access.InMemoryHangmanDataAccessObject;
import entity.HangmanGame;
import entity.Round;
import interface_adapter.endgame_results.EndGameResultsState.RoundResult;
import use_case.Stats.StatsDataAccessInterface; // Import the interface
import entity.GameStats; // Import GameStats
import constant.StatusConstant; // Corrected import for StatusConstant

import java.util.ArrayList;
import java.util.List;

public class EndGameResultsInteractor implements EndGameResultsInputBoundary {

    final EndGameResultsOutputBoundary EndGameResultsPresenter;
    final InMemoryHangmanDataAccessObject hangmanGameDAO; // Keep as concrete class
    final StatsDataAccessInterface statsDataAccess; // New field for stats DAO

    public EndGameResultsInteractor(EndGameResultsOutputBoundary EndGameResultsPresenter,
                                    InMemoryHangmanDataAccessObject hangmanGameDAO, // Keep as concrete class
                                    StatsDataAccessInterface statsDataAccess) { // Add to constructor
        this.EndGameResultsPresenter = EndGameResultsPresenter;
        this.hangmanGameDAO = hangmanGameDAO;
        this.statsDataAccess = statsDataAccess; // Initialize
    }

    @Override
    public void execute(EndGameResultsInputData inputData) {
        HangmanGame game = hangmanGameDAO.getHangmanGame();

        Round lastRound = game.getRounds().get(game.getRounds().size() - 1);
        boolean overallGameWon = lastRound.getStatus().equals(StatusConstant.WON);

        GameStats currentStats = statsDataAccess.loadStatistics(); // Load existing stats

        List<RoundResult> roundResults = new ArrayList<>();

        for (int i = 0; i < game.getRounds().size(); i++) {
            Round round = game.getRounds().get(i);
            String status = round.getStatus();

            if (status.equals(StatusConstant.WON)) {
                currentStats.incrementRoundsWon();
            } else {
                currentStats.incrementRoundsLost();
            }

            // Existing debug prints
            System.out.println("Round " + (i + 1) + ":");
            System.out.println("  Status: " + round.getStatus());
            System.out.println("  Remaining Attempts: " + round.getAttempt());
            System.out.println("  Attempts Used: " + (hangmanGameDAO.getMaxAttempts() - round.getAttempt()));

            int attemptsUsed = hangmanGameDAO.getMaxAttempts() - round.getAttempt();
            String word = new String(round.getWordPuzzle().getLetters());
            String statusText = status.equals(StatusConstant.WON) ? "Won" : "Lost";

            RoundResult result = new RoundResult(
                    i + 1,
                    word,
                    attemptsUsed,
                    statusText
            );
            roundResults.add(result);
        }

        statsDataAccess.saveStatistics(currentStats); // Save updated stats

        EndGameResultsPresenter.present(overallGameWon ? "You Won the Game!" : "You Lost the Game!", roundResults);
    }
}