package use_case.GenerateWord;

/**
 * The output boundary for the Generate Word Use Case.
 */

public interface GenerateWordOutputBoundary {
    /**
     * Prepares the success view for the Generate Word Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(GenerateWordOutputData outputData);

    void prepareFailureView(String error);
}