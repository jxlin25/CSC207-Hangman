package interface_adapter.generate_word;

import interface_adapter.make_guess.MakeGuessState;
import interface_adapter.ViewManagerModel;
import interface_adapter.make_guess.MakeGuessViewModel;

import use_case.generate_word.GenerateWordOutputBoundary;
import use_case.generate_word.GenerateWordOutputData;

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
        final GenerateWordState state = generateWordViewModel.getState();
        state.setWords(outputData.getWords());
        state.setError(null);
        generateWordViewModel.setState(state);
        generateWordViewModel.firePropertyChange();

        final MakeGuessState makeGuessState = makeGuessViewModel.getState();
        makeGuessViewModel.setState(makeGuessState);
        makeGuessViewModel.firePropertyChange();

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