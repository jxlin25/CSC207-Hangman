package use_case.MakeGuess;

import entity.Guess;

// With the output data here, the program can determine whether reveal the letter in the guess or not
public class MakeGuessOutputData {
    private Guess guess;
    private boolean isGuessCorrect;
    private String roundStatus;
    private boolean isGameOver;
    private int remainingAttempts;
    private int currentRoundNumber;
    private String maskedWord;


    public MakeGuessOutputData(Guess guess, boolean isGuessCorrect,  String roundStatus, boolean isGameOver, int remainingAttempts, int currentRoundNumber,  String maskedWord) {
        this.guess = guess;
        this.isGuessCorrect = isGuessCorrect;
        this.roundStatus = roundStatus;
        this.isGameOver = isGameOver;
        this.remainingAttempts = remainingAttempts;
        this.currentRoundNumber = currentRoundNumber;
        this.maskedWord = maskedWord;
    }

    public Guess getGuess() {return this.guess;}
    public boolean isGuessCorrect() {return this.isGuessCorrect;}
    public String  getRoundStatus() {return this.roundStatus;}
    public boolean isGameOver() {return this.isGameOver;}
    public int getRemainingAttempts() {return this.remainingAttempts;}
    public int getCurrentRoundNumber() {return this.currentRoundNumber;}
    public String getMaskedWord() {return this.maskedWord;}
}
