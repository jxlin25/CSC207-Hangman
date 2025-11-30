package use_case.InitializeRound;

import data_access.InMemoryHangmanDataAccessObject;
import entity.HangmanGame;
import org.junit.Test;
import use_case.MakeGuess.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link InitializeRoundInteractor}.
 *
 * These tests use a dummy presenter that implements {@link InitializeRoundOutputBoundary}
 * that simply captures the {@link InitializeRoundOutputData} passed to it.
 */
public class InitializeRoundInteractorTest {

    /**
     * A dummy presenter that can only return the outputData that was passed to it.
     */
    private static class DummyInitializeRoundPresenter implements InitializeRoundOutputBoundary {
        private InitializeRoundOutputData outputData;

        @Override
        public void initializeView(InitializeRoundOutputData outputData) {
            this.outputData = outputData;
        }

        public InitializeRoundOutputData getOutputData() {
            return outputData;
        }
    }

    @Test
    public void testInitializeRoundOutput() {

        // Initialize test dummies
        List<String> words = Arrays.asList("Apple", "Banana", "Cherry");
        HangmanGame hangmanGame = new HangmanGame(words);
        DummyInitializeRoundPresenter presenter = new DummyInitializeRoundPresenter();
        InMemoryHangmanDataAccessObject dao = new InMemoryHangmanDataAccessObject();
        dao.setHangmanGame(hangmanGame);

        InitializeRoundInputBoundary interactor = new InitializeRoundInteractor(presenter, dao);

        // Execute the interactor
        interactor.execute();

        InitializeRoundOutputData outputData = presenter.getOutputData();

        // Assert
        assertNotNull("Output data should not be null", outputData);
        assertEquals("Remaining attempts should be 6 by default", 6, outputData.getRemainingAttempts());
        assertEquals("Current round number should be 1", 1, outputData.getCurrentRoundNumber());
        assertEquals("Total round number should be 3", 3, outputData.getTotalRoundNumber());
        assertEquals("Masked word should be '_ _ _ _ _'", "_ _ _ _ _",  outputData.getMaskedWord());

    }
}
