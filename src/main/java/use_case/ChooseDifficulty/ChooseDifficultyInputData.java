package use_case.ChooseDifficulty;

public class ChooseDifficultyInputData {

    private final String difficulty; // e.g., "EASY", "NORMAL", "HARD"

    public ChooseDifficultyInputData(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }
}

