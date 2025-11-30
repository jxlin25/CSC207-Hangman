package interface_adapter.endgame_results;

import use_case.endgame_results.EndGameResultsInputBoundary;
import use_case.endgame_results.EndGameResultsInputData;

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