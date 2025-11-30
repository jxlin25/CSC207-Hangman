package use_case.ChooseDifficulty;

public class ChooseDifficultyInputData {

    private final int maxAttempts;
    private final int hintAttempts;

    public ChooseDifficultyInputData(int maxAttempts, int hintAttempts) {
        this.maxAttempts = maxAttempts;
        this.hintAttempts = hintAttempts;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getHintAttempts() {
        return hintAttempts;
    }
}

