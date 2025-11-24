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
     */

    public void execute(int generateNumber) {
        GenerateWordInputData inputData = new GenerateWordInputData(generateNumber);
        generateWordInputBoundary.execute(inputData);
    }
}