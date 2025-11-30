package use_case.generate_word;

/**
 * The output boundary for the Generate Word Use Case.
 */

public interface GenerateWordOutputBoundary {
    /**
     * Prepares the success view for the Generate Word Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(GenerateWordOutputData outputData);

    /**
     * Prepares the Failure view for the Generate Word Use Case.
     * @param error the explanation of the failure
     */
    void prepareFailureView(String error);
}
