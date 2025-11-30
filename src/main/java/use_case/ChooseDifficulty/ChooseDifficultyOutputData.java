package use_case.ChooseDifficulty;

public class ChooseDifficultyOutputData {

    //private final String difficulty;
    private final int maxAttempts;

    public ChooseDifficultyOutputData(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }
}

