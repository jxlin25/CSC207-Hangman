package entity;

public interface RoundState {
    /**
     * Handle a guess made in this round.
     * @param round the Round object
     * @param guess the Guess object that contains the letter
     */
    void handleGuess(Round round, Guess guess);

    /**
     * Check if the round is over.
     * @return true if the round is finished (won or lost).
     */
    boolean isOver();

    /**
     * Obtains the status of the current round.
     * @return the current status string (WAITING, GUESSING, WON, LOST).
     */
    String getStatus();
}
