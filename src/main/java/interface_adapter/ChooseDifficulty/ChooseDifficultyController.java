package interface_adapter.ChooseDifficulty;

import use_case.ChooseDifficulty.ChooseDifficultyInputBoundary;
import use_case.ChooseDifficulty.ChooseDifficultyInputData;

public class ChooseDifficultyController {

    private final ChooseDifficultyInputBoundary interactor;

    public ChooseDifficultyController(ChooseDifficultyInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(int maxAttempts) {
        ChooseDifficultyInputData inputData = new ChooseDifficultyInputData(maxAttempts);
        interactor.execute(inputData);
    }
}


