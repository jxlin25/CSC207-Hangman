package interface_adapter.EndGameResults;

import use_case.endgameresults.EndGameResultsInputBoundary;
import use_case.endgameresults.EndGameResultsInputData;

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