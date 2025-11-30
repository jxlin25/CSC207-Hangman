package use_case.GenerateWord;

import org.junit.Test;
import data_access.InMemoryHangmanDataAccessObject;
import entity.HangmanGame;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link GenerateWordInteractor}.
 */

public class GenerateWordInteractorTest {

    /**
     * A dummy Word API DAO that can only return a fixed word and verify whether
     * this word is valid as our the set.
     */
    private static class DummyGenerateWordDAO implements GenerateWordDataAccessInterface {
        String word;
        boolean valid = true;
        int callCount = 0;

        @Override
        public String getRandomWord() {
            callCount++;
            return word;
        }

        @Override
        public boolean isValidWord(String word) {
            return valid;
        }
    }

    /**
     * A dummy presenter that can only return the outputData that was passed to it.
     */
    private static class DummyPresenter implements GenerateWordOutputBoundary {
        GenerateWordOutputData outputData;
        String error;

        @Override
        public void prepareSuccessView(GenerateWordOutputData outputData) {
            this.outputData = outputData;
        }

        @Override
        public void prepareFailureView(String error) {
            this.error = error;
        }
    }

    @Test
    public void testSuccessfulGenerationUsingRealDAO() {
        // test successful
        DummyGenerateWordDAO wordDAO = new DummyGenerateWordDAO();
        DummyPresenter presenter = new DummyPresenter();
        InMemoryHangmanDataAccessObject hangmanDAO = new InMemoryHangmanDataAccessObject();

        // set DAO will return apple and is valid.
        wordDAO.word = "apple";
        wordDAO.valid = true;

        // enter data.
        GenerateWordInteractor interactor = new GenerateWordInteractor(wordDAO, presenter, hangmanDAO);
        interactor.execute(new GenerateWordInputData(2));

        // check data is equal to expect.
        assertNotNull("output data should have.", presenter.outputData);
        assertEquals(2, presenter.outputData.getWords().size());
        assertEquals("apple", presenter.outputData.getWords().get(0));

        // make sure HangmenDAO be set.
        HangmanGame game = hangmanDAO.getHangmanGame();
        assertNotNull("game should be created.", game);
        assertEquals(2, game.getRounds().size());
        assertNull("Should be no error", presenter.error);
    }

    @Test
    public void testFailureAfter10Attempts() {
        // test we can not get valid word.
        DummyGenerateWordDAO wordDAO = new DummyGenerateWordDAO();
        DummyPresenter presenter = new DummyPresenter();
        InMemoryHangmanDataAccessObject hangmanDAO = new InMemoryHangmanDataAccessObject();

        wordDAO.word = "DNE";
        wordDAO.valid = false;

        GenerateWordInteractor interactor = new GenerateWordInteractor(wordDAO, presenter, hangmanDAO);
        interactor.execute(new GenerateWordInputData(1));

        // Hangman should not be set up
        assertNull("Game should not be created on failure", hangmanDAO.getHangmanGame());
        assertNotNull("Error message expected", presenter.error);
        assertEquals("Failed to generate a valid word after 10 attempts, please try again!", presenter.error);

        assertTrue("API should be called at least 10 times", wordDAO.callCount >= 10);
    }

    @Test
    public void testNegativeNumberOfWords() {
        // test if enter <= 0, actually this doesn't happen.
        DummyGenerateWordDAO wordDAO = new DummyGenerateWordDAO();
        DummyPresenter presenter = new DummyPresenter();
        InMemoryHangmanDataAccessObject hangmanDAO = new InMemoryHangmanDataAccessObject();

        GenerateWordInteractor interactor = new GenerateWordInteractor(wordDAO, presenter, hangmanDAO);
        interactor.execute(new GenerateWordInputData(-1));

        assertEquals("Number of words must be langer than 0!!", presenter.error);
        assertNull(hangmanDAO.getHangmanGame());
    }
}
