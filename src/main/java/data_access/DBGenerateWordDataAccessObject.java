package data_access;

import entity.Guess;
import entity.HangmanGame;
import entity.Round;
import entity.WordPuzzle;
import use_case.GenerateWord.WordPuzzleDataAccessInterface;
import use_case.MakeGuess.MakeGuessHangmanGameDataAccessInterface;

public class DBGenerateWordDataAccessObject implements WordPuzzleDataAccessInterface, MakeGuessHangmanGameDataAccessInterface {

    private String word;

    public DBGenerateWordDataAccessObject() {
        word = "";
    }

    @Override
    public String getRandomWord() {
        //TODO
        return "apple";

    }

    @Override
    public void saveRandomWord(String randomWord) {
        this.word = randomWord;
    }

    @Override
    public String getSaveRandomWord() {
        return word;
    }


    @Override
    public boolean isValidWord(String word) {
        //TODO
        return true;
    }

    @Override
    public HangmanGame getHangmanGame() {
        return null;
    }

    @Override
    public Round getCurrentRound() {
        return null;
    }

    @Override
    public void setHangmanGame(HangmanGame hangmanGame) {

    }

    @Override
    public void decreaseCurrentRoundAttempt() {

    }

    @Override
    public void addGuessToCurrentRound(Guess guess) {

    }

    @Override
    public boolean setCurrentRoundWonAndStartNextRound() {
        return false;
    }

    @Override
    public boolean setCurrentRoundLostAndStartNextRound() {
        return false;
    }

    @Override
    public int getCurrentRoundAttempt() {
        return 0;
    }

    @Override
    public WordPuzzle getCurrentWordPuzzle() {
        return null;
    }

    @Override
    public boolean isGuessCorrect(Guess guess) {
        return false;
    }

    @Override
    public void revealLetter(Guess guess) {

    }

    @Override
    public boolean isPuzzleComplete() {
        return false;
    }

    @Override
    public char[] getWordPuzzle() {
        return new char[0];
    }

    @Override
    public boolean[] getRevealedLettersBooleans() {
        return new boolean[0];
    }

    @Override
    public int getCurrentRoundNumber() {
        return 0;
    }
}
