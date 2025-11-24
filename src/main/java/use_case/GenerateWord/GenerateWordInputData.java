package use_case.GenerateWord;

/**
 * We will use it later, but not now. So there is no content for the time being.
 */

public class GenerateWordInputData {
    private final int numbers;
    private final int attempts;

    public GenerateWordInputData(int numbers, int attempts) {
        this.numbers = numbers;
        this.attempts = attempts;
    }

    public int getNumbers() {
        return numbers;
    }

    public int getAttempts() {
        return attempts;
    }
}