package use_case.InitializeRound;

public class InitializeRoundOutputData {

    private final int remainingAttempts;
    private final int currentRoundNumber;
    private final String maskedWord;
    private final int totalRoundNumber;

    public InitializeRoundOutputData(int remainingAttempts,
                                     int currentRoundNumber,
                                     String maskedWord,
                                     int totalRoundNumber) {
        this.remainingAttempts = remainingAttempts;
        this.currentRoundNumber = currentRoundNumber;
        this.maskedWord = maskedWord;
        this.totalRoundNumber = totalRoundNumber;

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

    public int getTotalRoundNumber() {
        return totalRoundNumber;
    }
}
