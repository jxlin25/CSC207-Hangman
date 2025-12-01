package use_case.generate_hint;

import data_access.InMemoryHangmanDataAccessObject;
import entity.HangmanGame;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link HintInteractor}.
 */
public class HintInteractorTest {

    /**
     * A dummy Hint DAO that can only return a fixed hint and api key verify whether
     * this key is valid as our the set.
     */
    private static class DummyHintDAO implements HintDataAccessInterface {
        boolean apiValid = true;
        String gemiHint;
        String dictHint;

        @Override
        public boolean isApiKeyValid() {
            return apiValid;
        }

        @Override
        public String getGemiHint(String word) {
            return gemiHint;
        }

        @Override
        public String getDictHint(String word) {
            return dictHint;
        }
    }

    /**
     * A dummy presenter that can only return the outputData that was passed to it.
     */
    private static class DummyPresenter implements HintOutputBoundary {
        HintOutputData capturedData;

        @Override
        public void prepareSuccessView(HintOutputData hintOutputData) {
            this.capturedData = hintOutputData;
        }
    }

    @Test
    public void testValidApiKeyReturnsGeminiHint() {
        DummyHintDAO hintDAO = new DummyHintDAO();
        hintDAO.apiValid = true;
        hintDAO.gemiHint = "Gemini Hint";

        DummyPresenter presenter = new DummyPresenter();
        InMemoryHangmanDataAccessObject hangmanDAO = new InMemoryHangmanDataAccessObject();
        List<String> words = Arrays.asList("hint");
        HangmanGame hangmanGame = new HangmanGame(words);
        // set attempts = 2
        hangmanGame.setHintAttempts(2);
        hangmanDAO.setHangmanGame(hangmanGame);

        HintInteractor interactor = new HintInteractor(hintDAO, presenter, hangmanDAO);
        interactor.execute();

        assertEquals(1, presenter.capturedData.getRemainHintAttempts());
        assertEquals("Gemini Hint", presenter.capturedData.getHint());
    }

    @Test
    public void testInvalidApiKeyUsesDictionaryHint() {
        DummyHintDAO hintDAO = new DummyHintDAO();
        hintDAO.apiValid = false;
        hintDAO.dictHint = "Dictionary Hint";

        DummyPresenter presenter = new DummyPresenter();
        InMemoryHangmanDataAccessObject hangmanDAO = new InMemoryHangmanDataAccessObject();
        List<String> words = Arrays.asList("hint");
        HangmanGame hangmanGame = new HangmanGame(words);
        // set attempts = 6
        hangmanGame.setHintAttempts(6);
        hangmanDAO.setHangmanGame(hangmanGame);

        HintInteractor interactor = new HintInteractor(hintDAO, presenter, hangmanDAO);
        interactor.execute();

        assertEquals(5, presenter.capturedData.getRemainHintAttempts());
        assertEquals("You haven't set an API Key or the Key is invalid. Here is a hint from the dictionary: Dictionary Hint",
                presenter.capturedData.getHint());
    }

    @Test
    public void testGeminiHintIsNull() {
        DummyHintDAO hintDAO = new DummyHintDAO();
        hintDAO.apiValid = true;
        hintDAO.gemiHint = null;

        DummyPresenter presenter = new DummyPresenter();
        InMemoryHangmanDataAccessObject hangmanDAO = new InMemoryHangmanDataAccessObject();
        List<String> words = Arrays.asList("hint");
        HangmanGame hangmanGame = new HangmanGame(words);
        hangmanGame.setHintAttempts(1);
        hangmanDAO.setHangmanGame(hangmanGame);

        HintInteractor interactor = new HintInteractor(hintDAO, presenter, hangmanDAO);
        interactor.execute();

        assertEquals(0, presenter.capturedData.getRemainHintAttempts());
        assertEquals("No hint available.", presenter.capturedData.getHint());
    }

    @Test
    public void testDictionaryHintIsNull() {
        DummyHintDAO hintDAO = new DummyHintDAO();
        hintDAO.apiValid = false;
        hintDAO.dictHint = null;

        DummyPresenter presenter = new DummyPresenter();
        InMemoryHangmanDataAccessObject hangmanDAO = new InMemoryHangmanDataAccessObject();
        List<String> words = Arrays.asList("hint");
        HangmanGame hangmanGame = new HangmanGame(words);
        hangmanGame.setHintAttempts(1);
        hangmanDAO.setHangmanGame(hangmanGame);

        HintInteractor interactor = new HintInteractor(hintDAO, presenter, hangmanDAO);
        interactor.execute();

        assertEquals(0, presenter.capturedData.getRemainHintAttempts());
        assertEquals("No hint available.", presenter.capturedData.getHint());
    }

    @Test
    public void testDictionaryHintIsEmpty() {
        DummyHintDAO hintDAO = new DummyHintDAO();
        hintDAO.apiValid = false;
        hintDAO.dictHint = "";

        DummyPresenter presenter = new DummyPresenter();
        InMemoryHangmanDataAccessObject hangmanDAO = new InMemoryHangmanDataAccessObject();
        List<String> words = Arrays.asList("hint");
        HangmanGame hangmanGame = new HangmanGame(words);
        hangmanGame.setHintAttempts(1);
        hangmanDAO.setHangmanGame(hangmanGame);

        HintInteractor interactor = new HintInteractor(hintDAO, presenter, hangmanDAO);
        interactor.execute();

        assertEquals(0, presenter.capturedData.getRemainHintAttempts());
        assertEquals("No hint available.", presenter.capturedData.getHint());
    }

    @Test
    public void testGeminiHintIsNotEmpty() {
        DummyHintDAO hintDAO = new DummyHintDAO();
        hintDAO.apiValid = true;
        hintDAO.gemiHint = "";

        DummyPresenter presenter = new DummyPresenter();
        InMemoryHangmanDataAccessObject hangmanDAO = new InMemoryHangmanDataAccessObject();
        List<String> words = Arrays.asList("hint");
        HangmanGame hangmanGame = new HangmanGame(words);
        hangmanGame.setHintAttempts(1);
        hangmanDAO.setHangmanGame(hangmanGame);

        HintInteractor interactor = new HintInteractor(hintDAO, presenter, hangmanDAO);
        interactor.execute();

        assertEquals(0, presenter.capturedData.getRemainHintAttempts());
        assertEquals("No hint available.", presenter.capturedData.getHint());
    }

    @Test
    public void testNotAttempts() {
        DummyHintDAO hintDAO = new DummyHintDAO();
        hintDAO.apiValid = true;
        hintDAO.gemiHint = "hint";

        DummyPresenter presenter = new DummyPresenter();
        InMemoryHangmanDataAccessObject hangmanDAO = new InMemoryHangmanDataAccessObject();
        List<String> words = Arrays.asList("hint");
        HangmanGame hangmanGame = new HangmanGame(words);
        hangmanGame.setHintAttempts(0);
        hangmanDAO.setHangmanGame(hangmanGame);

        HintInteractor interactor = new HintInteractor(hintDAO, presenter, hangmanDAO);
        interactor.execute();

        assertEquals(0, presenter.capturedData.getRemainHintAttempts());
        assertEquals("Don't have hint attempts", presenter.capturedData.getHint());
    }
}
