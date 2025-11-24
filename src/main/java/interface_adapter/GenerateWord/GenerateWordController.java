package interface_adapter.GenerateWord;

import use_case.GenerateWord.GenerateWordInputBoundary;
import use_case.GenerateWord.GenerateWordInputData;

/**
 * Controller for the Generate Word Use Case.
 */

public class GenerateWordController {

    private final GenerateWordInputBoundary generateWordInputBoundary;

    public GenerateWordController(GenerateWordInputBoundary generateWordInputBoundary) {
        this.generateWordInputBoundary = generateWordInputBoundary;
    }

    /**
     * Executes the Generate Word Use Case.
     * @param generateNumber number of words to generate
     * @param attempts number of attempts based on difficulty selected
     */

    public void execute(int generateNumber, int attempts) {
        GenerateWordInputData inputData = new GenerateWordInputData(generateNumber, attempts);
        generateWordInputBoundary.execute(inputData);
    }
}