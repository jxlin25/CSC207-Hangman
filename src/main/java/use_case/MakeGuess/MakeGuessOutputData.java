package use_case.MakeGuess;

import entity.Guess;

// With the output data here, the program can determine whether reveal the letter in the guess or not
public class MakeGuessOutputData {
    private Guess guess;
    private boolean isGuessCorrect;
    private boolean isPuzzleComplete;


    public MakeGuessOutputData(Guess guess, boolean isGuessCorrect,  boolean isPuzzleComplete) {
        this.guess = guess;
        this.isGuessCorrect = isGuessCorrect;
        this.isPuzzleComplete = isPuzzleComplete;
    }

    public Guess getGuess() {return this.guess;}
    public boolean isGuessCorrect() {return this.isGuessCorrect;}
    public boolean isPuzzleComplete() {return this.isPuzzleComplete;}
}
