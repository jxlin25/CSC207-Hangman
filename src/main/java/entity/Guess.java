package entity;

/**
 * Represents a single guess made by the player, storing the guessed letter
 * and a boolean that indicates whether this guess is correct or not.
 */
public class Guess {

    private final char letter;

    public Guess(char letter) {
        this.letter = letter;
    }

    public char getLetter() {
        return letter;
    }

}