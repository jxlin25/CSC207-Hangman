package use_case.MakeGuess;

import entity.Guess;

// With the output data here, the program can determine whether reveal the letter in the guess or not
public class MakeGuessOutputData {
    private Guess guess;
    private boolean isGuessCorrect;
    private String roundStatus;
    private boolean isGameOver;
    private int remainingAttempts;
    private char[] letters;
    private boolean[] revealedLettersBooleans;
    private int currentRoundNumber;


    public MakeGuessOutputData(Guess guess, boolean isGuessCorrect,  String roundStatus, boolean isGameOver, int remainingAttempts,  char[] letters, boolean[] revealedLettersBooleans,  int currentRoundNumber) {
        this.guess = guess;
        this.isGuessCorrect = isGuessCorrect;
        this.roundStatus = roundStatus;
        this.isGameOver = isGameOver;
        this.remainingAttempts = remainingAttempts;
        this.letters = letters;
        this.revealedLettersBooleans = revealedLettersBooleans;
        this.currentRoundNumber = currentRoundNumber;
    }

    public Guess getGuess() {return this.guess;}
    public boolean isGuessCorrect() {return this.isGuessCorrect;}
    public String  getRoundStatus() {return this.roundStatus;}
    public boolean isGameOver() {return this.isGameOver;}
    public int getRemainingAttempts() {return this.remainingAttempts;}
    public char[] getLetters() {return this.letters;}
    public int getCurrentRoundNumber() {return this.currentRoundNumber;}

    public boolean[] getRevealedLettersBooleans() {
        return revealedLettersBooleans;
    }
}
