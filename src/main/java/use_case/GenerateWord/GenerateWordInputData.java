package use_case.GenerateWord;

/**
 * The input data for the GenerateWord Use Case.
 */

public class GenerateWordInputData {
    private final int numbers;

    public GenerateWordInputData(int numbers) {
        this.numbers = numbers;
    }

    public int getNumbers() {
        return numbers;
    }
}
