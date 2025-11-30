package use_case.Hint;

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
        hangmanDAO.setHangmanGame(hangmanGame);

        HintInteractor interactor = new HintInteractor(hintDAO, presenter, hangmanDAO);
        interactor.execute();

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
        hangmanDAO.setHangmanGame(hangmanGame);

        HintInteractor interactor = new HintInteractor(hintDAO, presenter, hangmanDAO);
        interactor.execute();

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
        hangmanDAO.setHangmanGame(hangmanGame);

        HintInteractor interactor = new HintInteractor(hintDAO, presenter, hangmanDAO);
        interactor.execute();

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
        hangmanDAO.setHangmanGame(hangmanGame);

        HintInteractor interactor = new HintInteractor(hintDAO, presenter, hangmanDAO);
        interactor.execute();

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
        hangmanDAO.setHangmanGame(hangmanGame);

        HintInteractor interactor = new HintInteractor(hintDAO, presenter, hangmanDAO);
        interactor.execute();

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
        hangmanDAO.setHangmanGame(hangmanGame);

        HintInteractor interactor = new HintInteractor(hintDAO, presenter, hangmanDAO);
        interactor.execute();

        assertEquals("No hint available.", presenter.capturedData.getHint());
    }
}
