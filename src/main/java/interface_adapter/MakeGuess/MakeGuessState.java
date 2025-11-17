package interface_adapter.MakeGuess;

import entity.Guess;

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
    private boolean guessCorrect;
    private String roundStatus;
    private String message = "";

    public MakeGuessState() {
    }

    public String getGuessedLetter() {
        return guessedLetter;
    }

    public void setGuessedLetter(String guessedLetter) {
        this.guessedLetter = guessedLetter;
    }

    public boolean isGuessCorrect() {
        return guessCorrect;
    }

    public void setGuessCorrect(boolean guessCorrect) {
        this.guessCorrect = guessCorrect;
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
}
    //TODO


