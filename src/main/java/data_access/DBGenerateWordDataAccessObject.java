package data_access;

import use_case.GenerateWord.WordPuzzleDataAccessInterface;

public class DBGenerateWordDataAccessObject implements WordPuzzleDataAccessInterface {

    public DBGenerateWordDataAccessObject() {}

    @Override
    public String getRandomWord() {
        //TODO
        return "apple";

    }


    @Override
    public boolean isValidWord(String word) {
        //TODO
        return true;
    }
}
