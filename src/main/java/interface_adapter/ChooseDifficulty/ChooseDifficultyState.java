package interface_adapter.ChooseDifficulty;

public class ChooseDifficultyState {

    private String difficulty = "NORMAL"; // default
    private int maxAttempts = 6;          // default
    private String error = null;

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
