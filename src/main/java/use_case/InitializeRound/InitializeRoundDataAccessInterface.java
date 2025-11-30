package use_case.InitializeRound;

import entity.Round;
import entity.WordPuzzle;

/**
 * The data access interface for the InitializeRound use case.
 */
public interface InitializeRoundDataAccessInterface {

    /**
     * Gets number of total rounds of the game.
     * @return number of total rounds of the game
     */
    int getTotalRoundNumber();

    /**
     * Gets the remaining attempts of the current Round.
     * @return the remaining attempts of the current Round
     */
    int getCurrentRoundAttempt();

    /**
     * Gets the round number of the current Round.
     * @return the round number of the current Round
     */
    int getCurrentRoundNumber();

    /**
     * Gets the string of the masked word of the current WordPuzzle.
     * @return the string of the masked word of the current WordPuzzle
     */
    String getCurrentMaskedWord();

}