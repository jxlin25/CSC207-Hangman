package use_case.EndGameResults;

import interface_adapter.EndGameResults.EndGameResultsState.RoundResult;
import java.util.List;

public interface EndGameResultsOutputBoundary {
    void present(String finalStatus, List<RoundResult> roundResults);
}