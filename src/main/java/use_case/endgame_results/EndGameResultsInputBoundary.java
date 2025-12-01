package use_case.endgame_results;

/**
 * The input data for the End Game Results Use Case.
 */
public interface EndGameResultsInputBoundary {
    /**
     * Execute the End Game Results Case.
     *
     * @param inputData the input data
     */
    void execute(EndGameResultsInputData inputData);
}

