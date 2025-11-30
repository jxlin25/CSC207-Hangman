package interface_adapter.endgame_results;

import interface_adapter.ViewManagerModel;
import use_case.endgame_results.EndGameResultsOutputBoundary;
import interface_adapter.endgame_results.EndGameResultsState.RoundResult;
import java.util.List;

public class EndGameResultsPresenter implements EndGameResultsOutputBoundary {

    private final EndGameResultsViewModel endGameResultsViewModel;
    private final ViewManagerModel viewManagerModel;

    public EndGameResultsPresenter(EndGameResultsViewModel endGameResultsViewModel, ViewManagerModel viewManagerModel) {
        this.endGameResultsViewModel = endGameResultsViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void present(String finalStatus, List<RoundResult> roundResults) {

        // Update the ViewModel State
        EndGameResultsState state = endGameResultsViewModel.getState();
        state.setFinalStatus(finalStatus);
        state.setRoundResults(roundResults);

        // Notify the Results View
        endGameResultsViewModel.firePropertyChange();

        // Switch the view to the results screen
        viewManagerModel.setState(endGameResultsViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}