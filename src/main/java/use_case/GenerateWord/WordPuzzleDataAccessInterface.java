package use_case.GenerateWord;

public interface WordPuzzleDataAccessInterface {
    String getRandomWord();

    void saveRandomWord(String randomWord);

    String getSaveRandomWord();

    boolean isValidWord(String word);
}