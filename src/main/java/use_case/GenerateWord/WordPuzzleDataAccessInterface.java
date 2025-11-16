package use_case.GenerateWord;

public interface WordPuzzleDataAccessInterface {

    String getRandomWord();

    boolean isValidWord(String word);
}