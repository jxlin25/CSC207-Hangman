package entity;

public class Guess {
    private char letter;
    //leave for multiplayer
    //private String playerId;

    public Guess(char letter) {
        this.letter = letter;
    }

    public char getLetter() {
        return letter;
    }
}
