package use_case.endgameresults;

import data_access.InMemoryHangmanDataAccessObject;
import entity.HangmanGame;
import entity.Round;
import interface_adapter.EndGameResults.EndGameResultsState.RoundResult;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for EndGameResultsInteractor.
 */
public class endgameresultsInteractorTest {

    /**
     * A dummy presenter that captures the output data.
     */
    private static class DummyEndGameResultsPresenter implements EndGameResultsOutputBoundary {
        private String finalStatus;
        private List<RoundResult> roundResults;

        @Override
        public void present(String finalStatus, List<RoundResult> roundResults) {
            this.finalStatus = finalStatus;
            this.roundResults = roundResults;
        }

        public String getFinalStatus() {
            return finalStatus;
        }

        public List<RoundResult> getRoundResults() {
            return roundResults;
        }
    }

    @Test
    public void testSingleRoundWon() {
        // Arrange
        List<String> words = Arrays.asList("cat");
        HangmanGame game = new HangmanGame(words);

        // Simulate winning the round
        Round round = game.getCurrentRound();
        round.startRound();
        round.setAttempt(4); // Used 2 attempts (6 - 4 = 2)
        round.setWon();

        InMemoryHangmanDataAccessObject dao = new InMemoryHangmanDataAccessObject();
        dao.setHangmanGame(game);

        DummyEndGameResultsPresenter presenter = new DummyEndGameResultsPresenter();
        EndGameResultsInteractor interactor = new EndGameResultsInteractor(presenter, dao);

        EndGameResultsInputData inputData = new EndGameResultsInputData();

        // Act
        interactor.execute(inputData);

        // Assert
        assertEquals("Final status should be 'Game Over'", "Game Over", presenter.getFinalStatus());

        List<RoundResult> results = presenter.getRoundResults();
        assertNotNull("Round results should not be null", results);
        assertEquals("Should have 1 round result", 1, results.size());

        RoundResult result = results.get(0);
        assertEquals("Round number should be 1", 1, result.getRoundNumber());
        assertEquals("Word should be 'cat'", "cat", result.getWord());
        assertEquals("Attempts used should be 2", 2, result.getAttemptsUsed());
        assertEquals("Status should be 'Won'", "Won", result.getStatus());
    }

    @Test
    public void testSingleRoundLost() {
        // Arrange
        List<String> words = Arrays.asList("dog");
        HangmanGame game = new HangmanGame(words);

        // Simulate losing the round
        Round round = game.getCurrentRound();
        round.startRound();
        round.setAttempt(0); // Used all 6 attempts
        round.setLost();
        
    }
    @Test
    public void testFiveRoundsMixedResults() {
        // Arrange - More rounds to ensure loop coverage
        List<String> words = Arrays.asList("one", "two", "three", "four", "five");
        HangmanGame game = new HangmanGame(words);

        // Round 1: Won with 1 attempt
        Round round1 = game.getRound(0);
        round1.startRound();
        round1.setAttempt(5);
        round1.setWon();

        // Round 2: Lost with all attempts
        Round round2 = game.getRound(1);
        round2.startRound();
        round2.setAttempt(0);
        round2.setLost();

        // Round 3: Won with 6 attempts (perfect game)
        Round round3 = game.getRound(2);
        round3.startRound();
        round3.setAttempt(6);
        round3.setWon();

        // Round 4: Won with 2 attempts
        Round round4 = game.getRound(3);
        round4.startRound();
        round4.setAttempt(4);
        round4.setWon();

        // Round 5: Lost with all attempts
        Round round5 = game.getRound(4);
        round5.startRound();
        round5.setAttempt(0);
        round5.setLost();

        InMemoryHangmanDataAccessObject dao = new InMemoryHangmanDataAccessObject();
        dao.setHangmanGame(game);

        DummyEndGameResultsPresenter presenter = new DummyEndGameResultsPresenter();
        EndGameResultsInteractor interactor = new EndGameResultsInteractor(presenter, dao);

        EndGameResultsInputData inputData = new use_case.endgameresults.EndGameResultsInputData();

        // Act
        interactor.execute(inputData);

        // Assert
        assertEquals("Final status should be 'Game Over'", "Game Over", presenter.getFinalStatus());

        List<RoundResult> results = presenter.getRoundResults();
        assertEquals("Should have 5 round results", 5, results.size());

        // Verify all rounds
        assertEquals("Round 1 status", "Won", results.get(0).getStatus());
        assertEquals("Round 2 status", "Lost", results.get(1).getStatus());
        assertEquals("Round 3 status", "Won", results.get(2).getStatus());
        assertEquals("Round 4 status", "Won", results.get(3).getStatus());
        assertEquals("Round 5 status", "Lost", results.get(4).getStatus());

        // Verify attempts for each round
        assertEquals("Round 1 attempts", 1, results.get(0).getAttemptsUsed());
        assertEquals("Round 2 attempts", 6, results.get(1).getAttemptsUsed());
        assertEquals("Round 3 attempts", 0, results.get(2).getAttemptsUsed());
        assertEquals("Round 4 attempts", 2, results.get(3).getAttemptsUsed());
        assertEquals("Round 5 attempts", 6, results.get(4).getAttemptsUsed());
    }
}