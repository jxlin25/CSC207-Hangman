package use_case.GenerateWord;

/**
 * The DAO interface for the Generate Word Use Case.
 */

public interface GenerateWordDataAccessInterface {
    String getRandomWord();

    void saveRandomWord(String randomWord);

    String getSaveRandomWord();

    boolean isValidWord(String word);
}