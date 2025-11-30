package use_case.ChooseDifficulty;

import Constant.AttemptsConstant;
import use_case.MakeGuess.HangmanGameDataAccessInterface;

/**
 * Interactor for the choose difficulty use case.
 *
 * <p>This class reads difficulty settings from the input data, updates the
 * data access object, and forwards the result to the output boundary.</p>
 */
public class ChooseDifficultyInteractor implements ChooseDifficultyInputBoundary {

    private final ChooseDifficultyOutputBoundary presenter;
    private final ChooseDifficultyDataAccessInterface chooseDifficultyDataAccessObject;

    /**
     * Creates a new interactor for handling difficulty selection.
     *
     * @param chooseDifficultyPresenter the output boundary that presents the chosen difficulty
     * @param chooseDifficultyDataAccessObject the data access object used to store difficulty settings
     */
    public ChooseDifficultyInteractor(ChooseDifficultyOutputBoundary chooseDifficultyPresenter, ChooseDifficultyDataAccessInterface chooseDifficultyDataAccessObject) {
        this.presenter = chooseDifficultyPresenter;
        this.chooseDifficultyDataAccessObject = chooseDifficultyDataAccessObject;
    }

    /**
     * Executes the choose difficulty use case.
     *
     * <p>The method reads the maximum attempts and hint attempts from the input data,
     * saves them to the data access object, creates an output data object and passes
     * it to the presenter.</p>
     *
     * @param inputData the input data that contains the selected difficulty settings
     */
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
