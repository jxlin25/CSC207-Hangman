package use_case.endgameresults;

// 1. Standard Java imports
import java.util.List;

// 2. Project / internal imports
import interface_adapter.EndGameResults.EndGameResultsState.RoundResult;

public interface EndGameResultsOutputBoundary {
    /**
     * Prepares the results table view for the End Game use case.
     *
     * @param finalStatus the final status of the game (e.g., "win" or "lose")
     * @param roundResults the list of round results to display
     */
    void present(String finalStatus, List<RoundResult> roundResults);
}

