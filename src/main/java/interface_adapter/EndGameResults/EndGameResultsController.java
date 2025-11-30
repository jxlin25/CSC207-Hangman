package interface_adapter.EndGameResults;

import use_case.EndGameResults.EndGameResultsInputBoundary;
import use_case.EndGameResults.EndGameResultsInputData;

public class EndGameResultsController {

    final EndGameResultsInputBoundary endGameResultsInteractor;

    public EndGameResultsController(EndGameResultsInputBoundary endGameResultsInteractor) {
        this.endGameResultsInteractor = endGameResultsInteractor;
    }

    public void execute() {
        // Creates the empty InputData object as required by the Input Boundary contract
        EndGameResultsInputData inputData = new EndGameResultsInputData();
        endGameResultsInteractor.execute(inputData);
    }
}