package use_case.MakeGuess;

import entity.Guess;

// With the output data here, the program can determine whether reveal the letter in the guess or not
public class MakeGuessOutputData {
    private Guess guess;
    private boolean isGuessCorrect;
    private boolean isPuzzleComplete;
    private boolean isGameOver;


    public MakeGuessOutputData(Guess guess, boolean isGuessCorrect,  boolean isPuzzleComplete, boolean isGameOver) {
        this.guess = guess;
        this.isGuessCorrect = isGuessCorrect;
        this.isPuzzleComplete = isPuzzleComplete;
        this.isGameOver = isGameOver;
    }

    public Guess getGuess() {return this.guess;}
    public boolean isGuessCorrect() {return this.isGuessCorrect;}
    public boolean isPuzzleComplete() {return this.isPuzzleComplete;}
    public boolean isGameOver() {return this.isGameOver;}
}
