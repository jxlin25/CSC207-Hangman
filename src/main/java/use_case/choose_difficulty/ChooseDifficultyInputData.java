package use_case.choose_difficulty;

/**
 * Input data for the choose difficulty use case.
 *
 * <p>This object is created when the user selects a difficulty and is passed to
 * the interactor so that the chosen settings can be processed.</p>
 */
public class ChooseDifficultyInputData {

    private final int maxAttempts;
    private final int hintAttempts;

    /**
     * Creates a new input data instance for the choose difficulty use case.
     *
     * @param maxAttempts the maximum number of attempts allowed for the game
     * @param hintAttempts the number of hints that can be used during the game
     */
    public ChooseDifficultyInputData(int maxAttempts, int hintAttempts) {
        this.maxAttempts = maxAttempts;
        this.hintAttempts = hintAttempts;
    }

    /**
     * Returns the maximum number of attempts allowed for the game.
     *
     * @return the maximum number of attempts
     */
    public int getMaxAttempts() {
        return maxAttempts;
    }

    /**
     * Returns the number of hints that can be used during the game.
     *
     * @return the number of hint attempts
     */
    public int getHintAttempts() {
        return hintAttempts;
    }
}
