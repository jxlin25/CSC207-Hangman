package use_case.ChooseDifficulty;

import Constant.AttemptsConstant;
import use_case.MakeGuess.HangmanGameDataAccessInterface;

public class ChooseDifficultyInteractor implements ChooseDifficultyInputBoundary {

    private final ChooseDifficultyOutputBoundary presenter;
    private final ChooseDifficultyDataAccessInterface chooseDifficultyDataAccessObject;


    public ChooseDifficultyInteractor(ChooseDifficultyOutputBoundary chooseDifficultyPresenter, ChooseDifficultyDataAccessInterface chooseDifficultyDataAccessObject) {
        this.presenter = chooseDifficultyPresenter;
        this.chooseDifficultyDataAccessObject = chooseDifficultyDataAccessObject;
    }

    @Override
    public void execute(ChooseDifficultyInputData inputData) {

        final int maxAttempts = inputData.getMaxAttempts();
        final int hintAttempts = inputData.getHintAttempts();

        chooseDifficultyDataAccessObject.setMaxAttempt(maxAttempts);
        chooseDifficultyDataAccessObject.setHintAttempts(hintAttempts);

//        int maxAttempts;
//
//        switch (difficulty.toUpperCase()) {
//            case "EASY":
//                maxAttempts = AttemptsConstant.EASY_ATTEMPTS;
//                difficulty = "EASY";
//                break;
//            case "HARD":
//                maxAttempts = AttemptsConstant.HARD_ATTEMPTS;
//                difficulty = "HARD";
//                break;
//            case "NORMAL":
//            default:
//                maxAttempts = AttemptsConstant.NORMAL_ATTEMPTS;
//                difficulty = "NORMAL";
//                break;
//        }

        final ChooseDifficultyOutputData outputData =
                new ChooseDifficultyOutputData(maxAttempts, hintAttempts);

        presenter.present(outputData);
    }
}

