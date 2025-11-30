package use_case.ChooseDifficulty;

public class ChooseDifficultyOutputData {

    //private final String difficulty;
    private final int maxAttempts;
    private final int hintAttempts;

    public ChooseDifficultyOutputData(int maxAttempts,  int hintAttempts) {
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

