package interface_adapter.MakeGuess;

import static Constant.StatusConstant.*;

public class MakeGuessState {

    private String guessedLetter = "";
    private boolean isGuessCorrect =  false;
    private String roundStatus = GUESSING;
    private boolean isGameOver = false;
    private int remainingAttempts = 6;
    private char[] letters;
    private boolean[] revealedLettersBooleans;
    private int currentRoundNumber = 1;
    private String message = "";


    public MakeGuessState() {

    }

    public MakeGuessState(String guessedLetter, boolean isGuessCorrect, boolean isGameOver, String roundStatus, int remainingAttempts, char[] letters, boolean[] revealedLettersBooleans, int currentRoundNumber) {
        this.guessedLetter = guessedLetter;
        this.isGuessCorrect = isGuessCorrect;
        this.isGameOver = isGameOver;
        this.roundStatus = roundStatus;
        this.remainingAttempts = remainingAttempts;
        this.letters = letters;
        this.revealedLettersBooleans = revealedLettersBooleans;
        this.currentRoundNumber = currentRoundNumber;
    }

    public String getGuessedLetter() {
        return guessedLetter;
    }

    public void setGuessedLetter(String guessedLetter) {
        this.guessedLetter = guessedLetter;
    }

    public boolean isGuessCorrect() {
        return isGuessCorrect;
    }

    public void setGuessCorrect(boolean guessCorrect) {
        this.isGuessCorrect = guessCorrect;
    }

    public String getRoundStatus() {
        return this.roundStatus;
    }

    public void setRoundStatus(String status) {
        this.roundStatus = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isGameOver() {
        return isGameOver;
    }
    public void setGameOver(boolean gameOver) {
        this.isGameOver = gameOver;
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    public void setRemainingAttempts(int remainingAttempts) {
        this.remainingAttempts = remainingAttempts;
    }

    public char[] getLetters() {
        return letters;
    }
    public void setLetters(char[] letters) {
        this.letters = letters;
    }
    public boolean[] getRevealedLettersBooleans() {
        return revealedLettersBooleans;
    }
    public void setRevealedLettersBooleans(boolean[] revealedLettersBooleans) {
        this.revealedLettersBooleans = revealedLettersBooleans;
    }

    public int getCurrentRoundNumber() {
        return currentRoundNumber;
    }
    public void setCurrentRoundNumber(int currentRoundNumber) {
        this.currentRoundNumber = currentRoundNumber;
    }
}



