package use_case.ChooseDifficulty;

public interface ChooseDifficultyDataAccessInterface {

    void setMaxAttempt(int maxAttempt);

    /**
     * Set hint max attempts.
     * @param hintAttempts hint max attempts.
     */
    void setHintAttempts(int hintAttempts);

    /**
     * get hint max attempts.
     * @return hint max attempts.
     */
    int getHintAttempts();
}
