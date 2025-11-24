package interface_adapter.GenerateWord;

import interface_adapter.MakeGuess.MakeGuessState;
import interface_adapter.ViewManagerModel;
import interface_adapter.MakeGuess.MakeGuessViewModel;

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
        //TODO I am not sure maybe we don't need this one, I also cannot test it(actually just change DAO return false but I still have another homework I don't want to test it rember delete it before submit)
        generateWordViewModel.setState(state);
        generateWordViewModel.firePropertyChange();
    }
}