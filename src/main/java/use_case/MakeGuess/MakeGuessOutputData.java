package use_case.MakeGuess;

import entity.Guess;

// With the output data here, the program can determine whether reveal the letter in the guess or not
public class MakeGuessOutputData {
    private Guess guess;
    private boolean isGuessCorrect;
    private String roundStatus;
    private boolean isGameOver;


    public MakeGuessOutputData(Guess guess, boolean isGuessCorrect,  String roundStatus, boolean isGameOver) {
        this.guess = guess;
        this.isGuessCorrect = isGuessCorrect;
        this.roundStatus = roundStatus;
        this.isGameOver = isGameOver;
    }

    public Guess getGuess() {return this.guess;}
    public boolean isGuessCorrect() {return this.isGuessCorrect;}
    public String  getRoundStatus() {return this.roundStatus;}
    public boolean isGameOver() {return this.isGameOver;}
}
