package interface_adapter.MakeGuess;

public class MakeGuessOutputData {
    private final String roundStatus; // "GUESSING", "WON", "LOST"
    private final String maskedWord;
    private final int remainingAttempts;
    private final String guessedLetters; // comma-separated
    private final String correctWordOnLoss; // non-null when LOST
    private final boolean gameOver;

    public MakeGuessOutputData(String roundStatus,
                               String maskedWord,
                               int remainingAttempts,
                               String guessedLetters,
                               String correctWordOnLoss,
                               boolean gameOver) {
        this.roundStatus = roundStatus;
        this.maskedWord = maskedWord;
        this.remainingAttempts = remainingAttempts;
        this.guessedLetters = guessedLetters;
        this.correctWordOnLoss = correctWordOnLoss;
        this.gameOver = gameOver;
    }

    public String getRoundStatus() { return roundStatus; }
    public String getMaskedWord() { return maskedWord; }
    public int getRemainingAttempts() { return remainingAttempts; }
    public String getGuessedLetters() { return guessedLetters; }
    public String getCorrectWordOnLoss() { return correctWordOnLoss; }
    public boolean isGameOver() { return gameOver; }
}