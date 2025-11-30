package use_case.ChooseDifficulty;

import Constant.AttemptsConstant;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for ChooseDifficultyInteractor to achieve 100% coverage.
 */
public class ChooseDifficultyInteractorTest {

    /**
     * Simple test double for the output boundary that stores the last output.
     */
    private static class TestPresenter implements ChooseDifficultyOutputBoundary {
        private ChooseDifficultyOutputData lastOutput;

        @Override
        public void present(ChooseDifficultyOutputData outputData) {
            this.lastOutput = outputData;
        }

        public ChooseDifficultyOutputData getLastOutput() {
            return lastOutput;
        }
    }

    /**
     * Simple test implementation of the input data.
     * If you already have a real ChooseDifficultyInputData class,
     * you can replace this with that class in the tests.
     */
    private static class TestInputData implements ChooseDifficultyInputData {
        private final String difficulty;

        TestInputData(String difficulty) {
            this.difficulty = difficulty;
        }

        @Override
        public String getDifficulty() {
            return difficulty;
        }
    }

    @Test
    public void execute_easyDifficulty_setsEasyAttemptsAndUppercaseLabel() {
        // Arrange
        TestPresenter presenter = new TestPresenter();
        ChooseDifficultyInteractor interactor = new ChooseDifficultyInteractor(presenter);
        ChooseDifficultyInputData input = new TestInputData("easy"); // lower-case to test toUpperCase()

        // Act
        interactor.execute(input);

        // Assert
        ChooseDifficultyOutputData output = presenter.getLastOutput();
        assertNotNull(output);  // no message arg, avoids overload confusion
        assertEquals("EASY", output.getDifficulty());
        assertEquals(AttemptsConstant.EASY_ATTEMPTS, output.getMaxAttempts());
    }

    @Test
    public void execute_hardDifficulty_setsHardAttemptsAndUppercaseLabel() {
        // Arrange
        TestPresenter presenter = new TestPresenter();
        ChooseDifficultyInteractor interactor = new ChooseDifficultyInteractor(presenter);
        ChooseDifficultyInputData input = new TestInputData("HaRd"); // mixed case

        // Act
        interactor.execute(input);

        // Assert
        ChooseDifficultyOutputData output = presenter.getLastOutput();
        assertNotNull(output);
        assertEquals("HARD", output.getDifficulty());
        assertEquals(AttemptsConstant.HARD_ATTEMPTS, output.getMaxAttempts());
    }

    @Test
    public void execute_normalDifficulty_setsNormalAttemptsAndUppercaseLabel() {
        // Arrange
        TestPresenter presenter = new TestPresenter();
        ChooseDifficultyInteractor interactor = new ChooseDifficultyInteractor(presenter);
        ChooseDifficultyInputData input = new TestInputData("NORMAL"); // already uppercase

        // Act
        interactor.execute(input);

        // Assert
        ChooseDifficultyOutputData output = presenter.getLastOutput();
        assertNotNull(output);
        assertEquals("NORMAL", output.getDifficulty());
        assertEquals(AttemptsConstant.NORMAL_ATTEMPTS, output.getMaxAttempts());
    }

    @Test
    public void execute_unknownDifficulty_defaultsToNormal() {
        // Arrange
        TestPresenter presenter = new TestPresenter();
        ChooseDifficultyInteractor interactor = new ChooseDifficultyInteractor(presenter);
        ChooseDifficultyInputData input = new TestInputData("invalid-value");

        // Act
        interactor.execute(input);

        // Assert
        ChooseDifficultyOutputData output = presenter.getLastOutput();
        assertNotNull(output);
        // For unknown difficulties, the code falls through to the default case and sets "NORMAL"
        assertEquals("NORMAL", output.getDifficulty());
        assertEquals(AttemptsConstant.NORMAL_ATTEMPTS, output.getMaxAttempts());
    }
}