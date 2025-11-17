package interface_adapter.GenerateWord;

import use_case.GenerateWord.GenerateWordOutputBoundary;
import use_case.GenerateWord.GenerateWordOutputData;

public class GenerateWordPresenter implements GenerateWordOutputBoundary {

    private final GenerateWordViewModel generateWordViewModel;

    public GenerateWordPresenter(GenerateWordViewModel generateWordViewModel) {
        this.generateWordViewModel = generateWordViewModel;
    }

    @Override
    public void prepareSuccessView(GenerateWordOutputData outputData) {
        final GenerateWordState state = generateWordViewModel.getState();
        state.setWordPuzzle(outputData.getPuzzle());
        generateWordViewModel.setState(state);
        generateWordViewModel.firePropertyChange();
    }
}