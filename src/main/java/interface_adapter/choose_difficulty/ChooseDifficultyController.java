package interface_adapter.choose_difficulty;

import use_case.choose_difficulty.ChooseDifficultyInputBoundary;
import use_case.choose_difficulty.ChooseDifficultyInputData;

public class ChooseDifficultyController {

    private final ChooseDifficultyInputBoundary interactor;

    public ChooseDifficultyController(ChooseDifficultyInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(int maxAttempts, int hintAttempts) {
        ChooseDifficultyInputData inputData = new ChooseDifficultyInputData(maxAttempts,  hintAttempts);
        interactor.execute(inputData);
    }
}


