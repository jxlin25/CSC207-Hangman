package interface_adapter.GenerateWord;

import use_case.GenerateWord.GenerateWordInputBoundary;

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

    public void execute() {
        generateWordInputBoundary.execute();
    }
}