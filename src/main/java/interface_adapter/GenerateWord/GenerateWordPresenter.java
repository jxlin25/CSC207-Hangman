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
        state.setWordPuzzle(outputData.getPuzzle());
        generateWordViewModel.setState(state);
        generateWordViewModel.firePropertyChange();

        MakeGuessState makeGuessState = makeGuessViewModel.getState();
        makeGuessViewModel.setState(makeGuessState);
        makeGuessViewModel.firePropertyChange();

        viewManagerModel.setState(makeGuessViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    //TODO fail view
}