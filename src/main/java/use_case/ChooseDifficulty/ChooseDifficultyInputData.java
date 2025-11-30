package use_case.ChooseDifficulty;

public class ChooseDifficultyInputData {

    private final int maxAttempts;

    public ChooseDifficultyInputData(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }
}

