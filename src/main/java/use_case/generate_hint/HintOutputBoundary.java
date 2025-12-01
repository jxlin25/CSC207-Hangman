package use_case.generate_hint;

/**
 * The output boundary for the Generate Word Use Case.
 */

public interface HintOutputBoundary {
    /**
     * Prepares the success view for the Hint Use Case.
     * @param hintOutputData the output data
     */

    void prepareSuccessView(HintOutputData hintOutputData);
}
