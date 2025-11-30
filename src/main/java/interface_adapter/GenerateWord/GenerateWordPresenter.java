package interface_adapter.GenerateWord;

import interface_adapter.MakeGuess.MakeGuessState;
import interface_adapter.MakeGuess.MakeGuessViewModel;
import interface_adapter.ViewManagerModel;
import use_case.GenerateWord.GenerateWordOutputBoundary;
import use_case.GenerateWord.GenerateWordOutputData;

/**
 * The Presenter for the Generate Word Use Case.
 */

public class GenerateWordPresenter implements GenerateWordOutputBoundary {

    private final GenerateWordViewModel generateWordViewModel;
    private final ViewManagerModel viewManagerModel;
    private final MakeGuessViewModel makeGuessViewModel;

    public GenerateWordPresenter(GenerateWordViewModel generateWordViewModel,
                                 MakeGuessViewModel makeGuessViewModel,
                                 ViewManagerModel viewManagerModel) {
        this.generateWordViewModel = generateWordViewModel;
        this.makeGuessViewModel = makeGuessViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(GenerateWordOutputData outputData) {
        // Update GenerateWord state
        final GenerateWordState state = generateWordViewModel.getState();
        state.setWords(outputData.getWords());
        state.setError(null);
        generateWordViewModel.setState(state);
        generateWordViewModel.firePropertyChange();
        // Update MakeGuess state with attempts from difficulty
        final MakeGuessState makeGuessState = makeGuessViewModel.getState();
        makeGuessState.setRemainingAttempts(outputData.getAttempts());
        makeGuessViewModel.setState(makeGuessState);
        makeGuessViewModel.firePropertyChange();
        // Switch view to MakeGuess
        viewManagerModel.setState(makeGuessViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailureView(String error) {
        final GenerateWordState state = generateWordViewModel.getState();
        state.setError(error);
        generateWordViewModel.firePropertyChange();
    }
}