package entity;

/**
 * Represents a single guess made by the player, storing the guessed letter
 * and a boolean that indicates whether this guess is correct or not.
 */
public class Guess {

    private final char letter;
    private boolean isCorrect;

    public Guess(char letter) {
        this.letter = letter;
        this.isCorrect = false;
    }

    public char getLetter() {
        return letter;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}