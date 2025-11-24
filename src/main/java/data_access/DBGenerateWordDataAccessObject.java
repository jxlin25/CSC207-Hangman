package data_access;

import use_case.GenerateWord.WordPuzzleDataAccessInterface;

public class DBGenerateWordDataAccessObject implements WordPuzzleDataAccessInterface {

    private String word;

    public DBGenerateWordDataAccessObject() {
    }

    @Override
    public String getRandomWord() {
        //TODO
        return "hello";

    }

    @Override
    public void saveRandomWord(String randomWord) {
        word = randomWord;
    }

    @Override
    public String getSaveRandomWord() {
        return word;
    }

    @Override
    public boolean isValidWord(String word) {
        return true;
    }

}