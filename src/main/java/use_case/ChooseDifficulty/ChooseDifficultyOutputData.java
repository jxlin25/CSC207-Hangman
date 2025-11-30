package use_case.ChooseDifficulty;

/**
 * Output data for the choose difficulty use case.
 *
 * <p>This object is created by the interactor after difficulty settings have
 * been applied and is passed to the presenter.</p>
 */
public class ChooseDifficultyOutputData {

    //private final String difficulty;
    private final int maxAttempts;
    private final int hintAttempts;

    /**
     * Creates a new output data instance for the choose difficulty use case.
     *
     * @param maxAttempts the maximum number of attempts associated with the chosen difficulty
     * @param hintAttempts the number of hints associated with the chosen difficulty
     */
    public ChooseDifficultyOutputData(int maxAttempts,  int hintAttempts) {
        this.maxAttempts = maxAttempts;
        this.hintAttempts = hintAttempts;
    }

    /**
     * Returns the maximum number of attempts associated with the chosen difficulty.
     *
     * @return the maximum number of attempts
     */
    public int getMaxAttempts() {
        return maxAttempts;
    }

    /**
     * Returns the number of hints associated with the chosen difficulty.
     *
     * @return the number of hint attempts
     */
    public int getHintAttempts() {
        return hintAttempts;
    }
}
