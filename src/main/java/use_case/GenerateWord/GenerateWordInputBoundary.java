package use_case.GenerateWord;

/**
 * The Generate Word Use Case.
 */

public interface GenerateWordInputBoundary {

    /**
     * Execute the Generate Word Use Case.
     * @param inputData the input data
     */
    void execute(GenerateWordInputData inputData);
}
