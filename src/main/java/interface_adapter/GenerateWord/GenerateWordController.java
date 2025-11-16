package interface_adapter.GenerateWord;

import use_case.GenerateWord.GenerateWordInputBoundary;

public class GenerateWordController {

    private final GenerateWordInputBoundary generateWordInputBoundary;

    public GenerateWordController(GenerateWordInputBoundary generateWordInputBoundary) {
        this.generateWordInputBoundary = generateWordInputBoundary;
    }

    public void execute() {
        generateWordInputBoundary.execute();
    }
}