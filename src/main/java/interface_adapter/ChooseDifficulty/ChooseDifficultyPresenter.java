package interface_adapter.ChooseDifficulty;

import interface_adapter.GenerateWord.GenerateWordState;
import interface_adapter.GenerateWord.GenerateWordViewModel;
import interface_adapter.MakeGuess.MakeGuessState;
import interface_adapter.MakeGuess.MakeGuessViewModel;
import interface_adapter.ViewManagerModel;
import use_case.ChooseDifficulty.ChooseDifficultyOutputBoundary;
import use_case.ChooseDifficulty.ChooseDifficultyOutputData;

public class ChooseDifficultyPresenter implements ChooseDifficultyOutputBoundary {

    private final ChooseDifficultyViewModel chooseDifficultyViewModel;
    private final GenerateWordViewModel generateWordViewModel;
    private final MakeGuessViewModel makeGuessViewModel;
    private final ViewManagerModel viewManagerModel;

    public ChooseDifficultyPresenter(ChooseDifficultyViewModel chooseDifficultyViewModel,
                                     GenerateWordViewModel generateWordViewModel,
                                     MakeGuessViewModel makeGuessViewModel,
                                     ViewManagerModel viewManagerModel) {
        this.chooseDifficultyViewModel = chooseDifficultyViewModel;
        this.generateWordViewModel = generateWordViewModel;
        this.makeGuessViewModel = makeGuessViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void present(ChooseDifficultyOutputData outputData) {
        // Update difficulty VM
        ChooseDifficultyState diffState = chooseDifficultyViewModel.getState();
        diffState.setDifficulty(outputData.getDifficulty());
        diffState.setMaxAttempts(outputData.getMaxAttempts());
        diffState.setError(null);
        chooseDifficultyViewModel.setState(diffState);
        chooseDifficultyViewModel.firePropertyChange();

        // Optionally also put difficulty/attempts into GenerateWordState
        GenerateWordState gwState = generateWordViewModel.getState();
        generateWordViewModel.setState(gwState);
        generateWordViewModel.firePropertyChange();

        // Initialize MakeGuessState with difficulty attempts:
        MakeGuessState mgState = makeGuessViewModel.getState();
        mgState.setRemainingAttempts(outputData.getMaxAttempts());
        mgState.setMaxAttempts(outputData.getMaxAttempts());
        makeGuessViewModel.setState(mgState);
        makeGuessViewModel.firePropertyChanged();

        // After choosing difficulty, go to GenerateWord (or anywhere you want)
        viewManagerModel.setState(generateWordViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
