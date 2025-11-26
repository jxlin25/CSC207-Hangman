package interface_adapter.EndGameResults;

import interface_adapter.ViewManagerModel;
import use_case.EndGameResults.EndGameResultsOutputBoundary;

public class EndGameResultsPresenter implements EndGameResultsOutputBoundary {

    private final EndGameResultsViewModel endGameResultsViewModel;
    private final ViewManagerModel viewManagerModel;

    public EndGameResultsPresenter(EndGameResultsViewModel endGameResultsViewModel, ViewManagerModel viewManagerModel) {
        this.endGameResultsViewModel = endGameResultsViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void present(String finalStatus, String finalWord, int attemptsTaken) {

        // 1. Update the ViewModel State
        EndGameResultsState state = endGameResultsViewModel.getState();
        state.setFinalStatus(finalStatus);
        state.setFinalWord(finalWord);
        state.setAttemptsTaken(attemptsTaken);

        // Notify the Results View
        endGameResultsViewModel.firePropertyChange();

        // 2. Switch the view to the results screen
        viewManagerModel.setState(endGameResultsViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}