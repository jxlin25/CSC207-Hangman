package use_case.endgame_results;

import interface_adapter.endgame_results.EndGameResultsState.RoundResult;
import java.util.List;

public interface EndGameResultsOutputBoundary {
    void present(String finalStatus, List<RoundResult> roundResults);
}