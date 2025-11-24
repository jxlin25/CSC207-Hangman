package use_case.MakeGuess;

import entity.Guess;

// With the output data here, the program can determine whether reveal the letter in the guess or not
public class MakeGuessOutputData {
    private final Guess guess;
    private final boolean isGuessCorrect;
    private final String roundStatus;
    private final boolean isGameOver;
    private final int remainingAttempts;
    private final int currentRoundNumber;
    private final String maskedWord;
    private final String guessedLetters;
    private final String correctWordOnLoss;


    public MakeGuessOutputData(Guess guess, boolean isGuessCorrect, String roundStatus, boolean isGameOver, int remainingAttempts, int currentRoundNumber,  String maskedWord, String guessedLetters, String correctWordOnLoss) {
        this.guess = guess;
        this.isGuessCorrect = isGuessCorrect;
        this.roundStatus = roundStatus;
        this.isGameOver = isGameOver;
        this.remainingAttempts = remainingAttempts;
        this.currentRoundNumber = currentRoundNumber;
        this.maskedWord = maskedWord;
        this.guessedLetters = guessedLetters;
        this.correctWordOnLoss = correctWordOnLoss;
    }

    public Guess getGuess() {
        return this.guess;
    }

    public boolean isGuessCorrect() {
        return this.isGuessCorrect;
    }

    public String getRoundStatus() {
        return this.roundStatus;
    }

    public boolean isGameOver() {
        return this.isGameOver;
    }

    public int getRemainingAttempts() {
        return this.remainingAttempts;
    }

    public int getCurrentRoundNumber() {
        return this.currentRoundNumber;
    }

    public String getMaskedWord() {
        return this.maskedWord;
    }
    public String getGuessedLetters() { return guessedLetters; }
    public String getCorrectWordOnLoss() { return correctWordOnLoss; }
}
