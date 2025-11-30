package interface_adapter.choose_difficulty;

import interface_adapter.make_guess.MakeGuessState;
import interface_adapter.make_guess.MakeGuessViewModel;
import use_case.choose_difficulty.ChooseDifficultyOutputBoundary;
import use_case.choose_difficulty.ChooseDifficultyOutputData;

public class ChooseDifficultyPresenter implements ChooseDifficultyOutputBoundary {

//    private final ChooseDifficultyViewModel chooseDifficultyViewModel;
//    private final GenerateWordViewModel generateWordViewModel;
    private final MakeGuessViewModel makeGuessViewModel;
//    private final ViewManagerModel viewManagerModel;

    public ChooseDifficultyPresenter(MakeGuessViewModel makeGuessViewModel) {
//        this.chooseDifficultyViewModel = chooseDifficultyViewModel;
//        this.generateWordViewModel = generateWordViewModel;
        this.makeGuessViewModel = makeGuessViewModel;
//        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void present(ChooseDifficultyOutputData outputData) {

        final MakeGuessState state = makeGuessViewModel.getState();

        state.setMaxAttempts(outputData.getMaxAttempts());
        state.setHintAttempts(outputData.getHintAttempts());

        makeGuessViewModel.setState(state);
//        // Update difficulty VM
//        ChooseDifficultyState diffState = chooseDifficultyViewModel.getState();
//        diffState.setDifficulty(outputData.getDifficulty());
//        diffState.setMaxAttempts(outputData.getMaxAttempts());
//        diffState.setError(null);
//        chooseDifficultyViewModel.setState(diffState);
//        chooseDifficultyViewModel.firePropertyChange();
//
//        // Optionally also put difficulty/attempts into GenerateWordState
//        GenerateWordState gwState = generateWordViewModel.getState();
//        generateWordViewModel.setState(gwState);
//        generateWordViewModel.firePropertyChange();
//
//        // Initialize MakeGuessState with difficulty attempts:
//        MakeGuessState mgState = makeGuessViewModel.getState();
//        mgState.setRemainingAttempts(outputData.getMaxAttempts());
//        mgState.setMaxAttempts(outputData.getMaxAttempts());
//        makeGuessViewModel.setState(mgState);
//        makeGuessViewModel.firePropertyChanged();
//
//
//        // After choosing difficulty, go to GenerateWord (or anywhere you want)
//        viewManagerModel.setState(generateWordViewModel.getViewName());
//        viewManagerModel.firePropertyChange();
    }
}
