package use_case.ChooseDifficulty;

public class ChooseDifficultyOutputData {

    private final String difficulty;
    private final int maxAttempts;

    public ChooseDifficultyOutputData(String difficulty, int maxAttempts) {
        this.difficulty = difficulty;
        this.maxAttempts = maxAttempts;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }
}

