package interface_adapter.MakeGuess;

import static Constant.StatusConstant.*;

public class MakeGuessState {
    /**
     * This class is the State of MarkGuess is stored. I think it might contain
     * information such as which letter the player guessed, where it was
     * located, and whether the guess was correct.
     * <p>
     * e.g.
     * guess.getletter -> "a"
     * located - > 3  (the third letter is a)
     * istrue() - > true - > it true letter
     * <p>
     * then view- >  ------   to  --a---
     */
    private String guessedLetter = "";
    private boolean isGuessCorrect =  false;
    private String roundStatus = GUESSING;
    private boolean isGameOver = false;
    private int remainingAttempts = 6;
    private String message = "";

    public MakeGuessState() {

    }

    public MakeGuessState(String guessedLetter, boolean isGuessCorrect, boolean isGameOver, String roundStatus, int remainingAttempts) {
        this.guessedLetter = guessedLetter;
        this.isGuessCorrect = isGuessCorrect;
        this.isGameOver = isGameOver;
        this.roundStatus = roundStatus;
        this.remainingAttempts = remainingAttempts;
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
}
    //TODO


