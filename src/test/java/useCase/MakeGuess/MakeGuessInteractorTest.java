package useCase.MakeGuess;

import entity.Guess;
import entity.HangmanGame;
import org.junit.Test;
import use_case.MakeGuess.*;
import data_access.InMemoryHangmanDataAccessObject;

import java.util.Arrays;
import java.util.List;

import static Constant.StatusConstant.*;
import static org.junit.Assert.*;

/**
 * Unit tests for {@link MakeGuessInteractor}.
 *
 * These tests use a dummy presenter that implements {@link MakeGuessOutputBoundary}
 * that simply captures the {@link MakeGuessOutputData} passed to it.
 */
public class MakeGuessInteractorTest {
    /**
     * A dummy presenter that can only return the outputData that was passed to it.
     */
    private static class DummyMakeGuessPresenter implements MakeGuessOutputBoundary {
        private MakeGuessOutputData outputData;

        @Override
        public void updateView(MakeGuessOutputData outputData) {
            this.outputData = outputData;
        }

        public MakeGuessOutputData getOutputData() {
            return outputData;
        }
    }

    @Test
    public void testCorrectGuessWithIncompleteRound() {

        // Initialize test dummies
        List<String> words = Arrays.asList("Apple");
        HangmanGame hangmanGame = new HangmanGame(words);
        DummyMakeGuessPresenter presenter = new DummyMakeGuessPresenter();
        InMemoryHangmanDataAccessObject dao = new InMemoryHangmanDataAccessObject();
        dao.setHangmanGame(hangmanGame);

        MakeGuessInteractor interactor = new MakeGuessInteractor(presenter, dao);

        Guess guess = new Guess('a');
        MakeGuessInputData inputData = new MakeGuessInputData(guess);

        // Execute the interactor

        // Correct guess made
        interactor.execute(inputData);

        MakeGuessOutputData outputData = presenter.getOutputData();

        // Assert
        assertNotNull("Output data should not be null", outputData);
        assertEquals("Guessed letter should remain the same in output data", 'a', outputData.getGuess().getLetter());
        assertTrue("Guess should be correct", outputData.isGuessCorrect());
        assertEquals("Round status should remain GUESSING", GUESSING, outputData.getRoundStatus());
        assertFalse("Game should not be over with unfinished round(s) remain", outputData.isGameOver());
        assertEquals("RemainingAttempts should remain 6 since the guess is correct", 6, outputData.getRemainingAttempts());
        assertEquals("Letter 'A' should be revealed", outputData.getMaskedWord(), "A _ _ _ _");

    }

    @Test
    public void testIncorrectGuessWithIncompleteRound() {

        // Initialize test dummies
        List<String> words = Arrays.asList("Apple");
        HangmanGame hangmanGame = new HangmanGame(words);
        DummyMakeGuessPresenter presenter = new DummyMakeGuessPresenter();
        InMemoryHangmanDataAccessObject dao = new InMemoryHangmanDataAccessObject();
        dao.setHangmanGame(hangmanGame);

        MakeGuessInteractor interactor = new MakeGuessInteractor(presenter, dao);

        Guess guess = new Guess('b');
        MakeGuessInputData inputData = new MakeGuessInputData(guess);

        // Execute the interactor

        // Incorrect guess made
        interactor.execute(inputData);

        MakeGuessOutputData outputData = presenter.getOutputData();

        // Assert
        assertNotNull("Output data should not be null", outputData);
        assertEquals("Guessed letter should remain the same in output data", 'b', outputData.getGuess().getLetter());
        assertFalse("Guess should be incorrect", outputData.isGuessCorrect());
        assertEquals("Round status should remain GUESSING", GUESSING, outputData.getRoundStatus());
        assertFalse("Game should not be over with unfinished round(s) remain", outputData.isGameOver());
        assertEquals("RemainingAttempts should be decreased to 5 since the guess is incorrect", 5, outputData.getRemainingAttempts());
        assertEquals("No letter should be revealed", outputData.getMaskedWord(), "_ _ _ _ _");

    }

    @Test
    public void testWonRoundWithUnendedGame() {

        // Initialize test dummies
        List<String> words = Arrays.asList("Apple", "Banana");
        HangmanGame hangmanGame = new HangmanGame(words);
        DummyMakeGuessPresenter presenter = new DummyMakeGuessPresenter();
        InMemoryHangmanDataAccessObject dao = new InMemoryHangmanDataAccessObject();
        dao.setHangmanGame(hangmanGame);

        MakeGuessInteractor interactor = new MakeGuessInteractor(presenter, dao);
        Guess guess = new Guess('a');
        MakeGuessInputData inputData = new MakeGuessInputData(guess);

        // Execute the interactor

        // Correct guess made
        interactor.execute(inputData);

        // Correct guess made
        guess = new Guess('p');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Incorrect guess made
        guess = new Guess('k');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Correct guess made
        guess = new Guess('l');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Incorrect guess made
        guess = new Guess('r');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Correct guess made
        guess = new Guess('e');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        MakeGuessOutputData outputData = presenter.getOutputData();

        // Assert
        assertNotNull("Output data should not be null", outputData);
        assertEquals("Last guessed letter should remain the same in output data", 'e', outputData.getGuess().getLetter());
        assertTrue("Guess should be correct", outputData.isGuessCorrect());
        assertEquals("Round status should be WON", WON, outputData.getRoundStatus());
        assertFalse("Game should not be over with unfinished round(s) remain", outputData.isGameOver());
        assertEquals("RemainingAttempts should be 4 since two incorrect guesses were made", 4, outputData.getRemainingAttempts());
        assertEquals("All letters should be revealed", outputData.getMaskedWord(), "A P P L E");

    }

    @Test
    public void testLostRoundWithUnendedGame() {

        // Initialize test dummies
        List<String> words = Arrays.asList("Apple", "Banana");
        HangmanGame hangmanGame = new HangmanGame(words);
        DummyMakeGuessPresenter presenter = new DummyMakeGuessPresenter();
        InMemoryHangmanDataAccessObject dao = new InMemoryHangmanDataAccessObject();
        dao.setHangmanGame(hangmanGame);
        MakeGuessInteractor interactor = new MakeGuessInteractor(presenter, dao);
        Guess guess = new Guess('a');
        MakeGuessInputData inputData = new MakeGuessInputData(guess);

        // Execute the interactor

        // Correct guess
        interactor.execute(inputData);

        // Incorrect guess made
        guess = new Guess('k');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Incorrect guess made
        guess = new Guess('r');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Incorrect guess made
        guess = new Guess('u');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Correct guess
        guess = new Guess('p');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Incorrect guess made
        guess = new Guess('i');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Incorrect guess made
        guess = new Guess('t');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Incorrect guess made
        guess = new Guess('m');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);


        MakeGuessOutputData outputData = presenter.getOutputData();

        // Assert
        assertNotNull("Output data should not be null", outputData);
        assertEquals("Last guessed letter should remain the same in output data", 'm', outputData.getGuess().getLetter());
        assertFalse("Guess should be incorrect", outputData.isGuessCorrect());
        assertEquals("Round status should be LOST", LOST, outputData.getRoundStatus());
        assertFalse("Game should not be over with unfinished round(s) remain", outputData.isGameOver());
        assertEquals("RemainingAttempts should be 0 since all 6 attempts were used", 0, outputData.getRemainingAttempts());
        assertEquals("Letter 'A' and 'P' should be revealed", outputData.getMaskedWord(), "A P P _ _");

    }

    @Test
    public void testWonLastRound() {

        // Initialize test dummies
        List<String> words = Arrays.asList("Apple");
        HangmanGame hangmanGame = new HangmanGame(words);
        DummyMakeGuessPresenter presenter = new DummyMakeGuessPresenter();
        InMemoryHangmanDataAccessObject dao = new InMemoryHangmanDataAccessObject();
        dao.setHangmanGame(hangmanGame);

        MakeGuessInteractor interactor = new MakeGuessInteractor(presenter, dao);
        Guess guess = new Guess('a');
        MakeGuessInputData inputData = new MakeGuessInputData(guess);

        // Execute the interactor

        // Correct guess made
        interactor.execute(inputData);

        // Correct guess made
        guess = new Guess('p');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Incorrect guess made
        guess = new Guess('k');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Correct guess made
        guess = new Guess('l');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Incorrect guess made
        guess = new Guess('r');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Correct guess made
        guess = new Guess('e');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        MakeGuessOutputData outputData = presenter.getOutputData();

        // Assert
        assertNotNull("Output data should not be null", outputData);
        assertEquals("Last guessed letter should remain the same in output data", 'e', outputData.getGuess().getLetter());
        assertTrue("Guess should be correct", outputData.isGuessCorrect());
        assertEquals("Round status should be WON", WON, outputData.getRoundStatus());
        assertTrue("Game should be over since all the round were finished", outputData.isGameOver());
        assertEquals("RemainingAttempts should be 4 since two incorrect guesses were made", 4, outputData.getRemainingAttempts());
        assertEquals("All letters should be revealed", outputData.getMaskedWord(), "A P P L E");

    }

    @Test
    public void testLostLastRound() {

        // Initialize test dummies
        List<String> words = Arrays.asList("Apple");
        HangmanGame hangmanGame = new HangmanGame(words);
        DummyMakeGuessPresenter presenter = new DummyMakeGuessPresenter();
        InMemoryHangmanDataAccessObject dao = new InMemoryHangmanDataAccessObject();
        dao.setHangmanGame(hangmanGame);
        MakeGuessInteractor interactor = new MakeGuessInteractor(presenter, dao);
        Guess guess = new Guess('a');
        MakeGuessInputData inputData = new MakeGuessInputData(guess);

        // Execute the interactor

        // Correct guess
        interactor.execute(inputData);

        // Incorrect guess made
        guess = new Guess('k');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Incorrect guess made
        guess = new Guess('r');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Incorrect guess made
        guess = new Guess('u');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Correct guess
        guess = new Guess('p');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Incorrect guess made
        guess = new Guess('i');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Incorrect guess made
        guess = new Guess('t');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);

        // Incorrect guess made
        guess = new Guess('m');
        inputData = new MakeGuessInputData(guess);
        interactor.execute(inputData);


        MakeGuessOutputData outputData = presenter.getOutputData();

        // Assert
        assertNotNull("Output data should not be null", outputData);
        assertEquals("Last guessed letter should remain the same in output data", 'm', outputData.getGuess().getLetter());
        assertFalse("Guess should be incorrect", outputData.isGuessCorrect());
        assertEquals("Round status should be LOST", LOST, outputData.getRoundStatus());
        assertTrue("Game should be over since all the round were finished", outputData.isGameOver());
        assertEquals("RemainingAttempts should be 0 since all 6 attempts were used", 0, outputData.getRemainingAttempts());
        assertEquals("Letter 'A' and 'P' should be revealed", outputData.getMaskedWord(), "A P P _ _");

    }

//    @Test
//    public void testIncorrectGuessDecreasesAttemptsAndStatusGuessing() {
//        // Arrange
//        TestMakeGuessPresenter presenter = new TestMakeGuessPresenter();
//        TestHangmanGameDataAccessObject dao = new TestHangmanGameDataAccessObject("apple", 6);
//        MakeGuessInteractor interactor = new MakeGuessInteractor(presenter, dao);
//
//        Guess guess = new Guess('z'); // wrong letter
//        MakeGuessInputData inputData = new MakeGuessInputData(guess);
//
//        // Act
//        interactor.execute(inputData);
//        MakeGuessOutputData out = presenter.getOutputData();
//
//        // Assert
//        assertNotNull(out);
//        assertFalse("Guess should be incorrect", out.isGuessCorrect());
//        assertEquals("Round status should remain GUESSING", GUESSING, out.getRoundStatus());
//        assertFalse("Game should not be over after first wrong guess", out.isGameOver());
//
//        // IMPORTANT: in your interactor, remainingAttempts is captured
//        // BEFORE decreaseCurrentRoundAttempt() is called.
//        // So the output still shows the attempts BEFORE this guess.
//        assertEquals("Remaining attempts returned from interactor is the pre-guess value",
//                6, out.getRemainingAttempts());
//    }
//
//    @Test
//    public void testWinningGuessSetsStatusWonAndGameOverWhenNoNextRound() {
//        // Arrange: a single-letter word "a", 3 attempts
//        TestMakeGuessPresenter presenter = new TestMakeGuessPresenter();
//        TestHangmanGameDataAccessObject dao = new TestHangmanGameDataAccessObject("a", 3);
//        MakeGuessInteractor interactor = new MakeGuessInteractor(presenter, dao);
//
//        Guess guess = new Guess('a'); // correct and will complete the puzzle
//        MakeGuessInputData inputData = new MakeGuessInputData(guess);
//
//        // Act
//        interactor.execute(inputData);
//        MakeGuessOutputData out = presenter.getOutputData();
//
//        // Assert
//        assertNotNull(out);
//        assertTrue(out.isGuessCorrect());
//        assertEquals("Round should be WON when puzzle is completed", WON, out.getRoundStatus());
//        assertTrue("Game should be over because DAO has no next round", out.isGameOver());
//        assertEquals(3, out.getRemainingAttempts());
//    }
//
//    @Test
//    public void testLastWrongGuessSetsStatusLostAndGameOver() {
//        // Arrange: word "a", but we start with only 1 attempt left
//        TestMakeGuessPresenter presenter = new TestMakeGuessPresenter();
//        TestHangmanGameDataAccessObject dao = new TestHangmanGameDataAccessObject("a", 1);
//        MakeGuessInteractor interactor = new MakeGuessInteractor(presenter, dao);
//
//        Guess guess = new Guess('z'); // incorrect
//        MakeGuessInputData inputData = new MakeGuessInputData(guess);
//
//        // Act
//        interactor.execute(inputData);
//        MakeGuessOutputData out = presenter.getOutputData();
//
//        // Assert
//        assertNotNull(out);
//        assertFalse(out.isGuessCorrect());
//        assertEquals("Round should be LOST after using final attempt", LOST, out.getRoundStatus());
//        assertTrue("Game should be over after last wrong guess", out.isGameOver());
//
//        // remainingAttempts in output is captured BEFORE deduction -> 1
//        assertEquals(1, out.getRemainingAttempts());
//    }
}
