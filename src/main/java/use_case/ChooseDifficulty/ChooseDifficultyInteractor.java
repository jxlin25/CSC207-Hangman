package use_case.ChooseDifficulty;

import Constant.AttemptsConstant;

public class ChooseDifficultyInteractor implements ChooseDifficultyInputBoundary {

    private final ChooseDifficultyOutputBoundary presenter;

    public ChooseDifficultyInteractor(ChooseDifficultyOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(ChooseDifficultyInputData inputData) {
        String difficulty = inputData.getDifficulty();
        int maxAttempts;

        switch (difficulty.toUpperCase()) {
            case "EASY":
                maxAttempts = AttemptsConstant.EASY_ATTEMPTS;
                difficulty = "EASY";
                break;
            case "HARD":
                maxAttempts = AttemptsConstant.HARD_ATTEMPTS;
                difficulty = "HARD";
                break;
            case "NORMAL":
            default:
                maxAttempts = AttemptsConstant.NORMAL_ATTEMPTS;
                difficulty = "NORMAL";
                break;
        }

        ChooseDifficultyOutputData outputData =
                new ChooseDifficultyOutputData(difficulty, maxAttempts);

        presenter.present(outputData);
    }
}
