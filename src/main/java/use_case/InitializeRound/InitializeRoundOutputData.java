package use_case.InitializeRound;

public class InitializeRoundOutputData {

    private int remainingAttempts;
    private int currentRoundNumber;
    private String maskedWord;

    public InitializeRoundOutputData(int remainingAttempts, int currentRoundNumber, String maskedWord) {
        this.remainingAttempts = remainingAttempts;
        this.currentRoundNumber = currentRoundNumber;
        this.maskedWord = maskedWord;
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    public int getCurrentRoundNumber() {
        return currentRoundNumber;
    }

    public String getMaskedWord() {
        return maskedWord;
    }
}
