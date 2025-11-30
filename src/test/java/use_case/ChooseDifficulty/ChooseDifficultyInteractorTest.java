package use_case.ChooseDifficulty;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for ChooseDifficultyInteractor to achieve 100% coverage.
 */
public class ChooseDifficultyInteractorTest {

    /**
     * Simple in-memory test double for the data access interface.
     * It stores the last values written by the interactor.
     */
    private static class TestDataAccess implements ChooseDifficultyDataAccessInterface {
        private int storedMaxAttempts;
        private int storedHintAttempts;

        @Override
        public void setMaxAttempt(int maxAttempts) {
            this.storedMaxAttempts = maxAttempts;
        }

        @Override
        public void setHintAttempts(int hintAttempts) {
            this.storedHintAttempts = hintAttempts;
        }

        @Override
        public int getHintAttempts() {
            return storedHintAttempts;
        }

        int getStoredMaxAttempts() {
            return storedMaxAttempts;
        }

        int getStoredHintAttempts() {
            return storedHintAttempts;
        }
    }

    /**
     * Simple test double for the output boundary that stores the last output.
     */
    private static class TestPresenter implements ChooseDifficultyOutputBoundary {
        private ChooseDifficultyOutputData lastOutput;

        @Override
        public void present(ChooseDifficultyOutputData outputData) {
            this.lastOutput = outputData;
        }

        ChooseDifficultyOutputData getLastOutput() {
            return lastOutput;
        }
    }

    @Test
    public void execute_storesValuesInDao_andPresentsOutput_basicCase() {
        // Arrange
        TestPresenter presenter = new TestPresenter();
        TestDataAccess dataAccess = new TestDataAccess();
        ChooseDifficultyInteractor interactor =
                new ChooseDifficultyInteractor(presenter, dataAccess);

        int maxAttempts = 10;
        int hintAttempts = 3;
        ChooseDifficultyInputData inputData =
                new ChooseDifficultyInputData(maxAttempts, hintAttempts);

        // Act
        interactor.execute(inputData);

        // Assert DAO interaction
        assertEquals("DAO should store maxAttempts",
                maxAttempts, dataAccess.getStoredMaxAttempts());
        assertEquals("DAO should store hintAttempts",
                hintAttempts, dataAccess.getStoredHintAttempts());
        assertEquals("DAO.getHintAttempts should return stored hintAttempts",
                hintAttempts, dataAccess.getHintAttempts());

        // Assert presenter interaction
        ChooseDifficultyOutputData output = presenter.getLastOutput();
        assertNotNull("Presenter should receive an output", output);
        assertEquals("Output maxAttempts should match input",
                maxAttempts, output.getMaxAttempts());
        assertEquals("Output hintAttempts should match input",
                hintAttempts, output.getHintAttempts());
    }

    @Test
    public void execute_withDifferentValues_stillPassesThemThrough() {
        // Arrange
        TestPresenter presenter = new TestPresenter();
        TestDataAccess dataAccess = new TestDataAccess();
        ChooseDifficultyInteractor interactor =
                new ChooseDifficultyInteractor(presenter, dataAccess);

        int maxAttempts = 4;
        int hintAttempts = 1;
        ChooseDifficultyInputData inputData =
                new ChooseDifficultyInputData(maxAttempts, hintAttempts);

        // Act
        interactor.execute(inputData);

        // Assert DAO interaction
        assertEquals(maxAttempts, dataAccess.getStoredMaxAttempts());
        assertEquals(hintAttempts, dataAccess.getStoredHintAttempts());
        assertEquals(hintAttempts, dataAccess.getHintAttempts());

        // Assert presenter interaction
        ChooseDifficultyOutputData output = presenter.getLastOutput();
        assertNotNull(output);
        assertEquals(maxAttempts, output.getMaxAttempts());
        assertEquals(hintAttempts, output.getHintAttempts());
    }
}
