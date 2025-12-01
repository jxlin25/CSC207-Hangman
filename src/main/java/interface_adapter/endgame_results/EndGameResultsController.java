package interface_adapter.endgame_results;

import use_case.endgame_results.EndGameResultsInputBoundary;
import use_case.endgame_results.EndGameResultsInputData;

public class EndGameResultsController {

    private final EndGameResultsInputBoundary endGameResultsInteractor;

    public EndGameResultsController(EndGameResultsInputBoundary endGameResultsInteractor) {
        this.endGameResultsInteractor = endGameResultsInteractor;
    }
    /**
     * Executes the Generate Word Use Case.
     *  creates an empty end game results object and passes it to the input boundary to perform use case.
     */

    public void execute() {
        final EndGameResultsInputData inputData = new EndGameResultsInputData();
        endGameResultsInteractor.execute(inputData);
    }
}