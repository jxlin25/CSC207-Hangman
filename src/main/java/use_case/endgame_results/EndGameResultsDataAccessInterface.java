package use_case.endgame_results;

import entity.HangmanGame;

public interface EndGameResultsDataAccessInterface {

    /**
     * Gets the maximum attempts for the game.
     * @return the maximum attempts for the game
     */
    int getMaxAttempts();

    /**
     * Gets the HangmanGame entity.
     * @return the HangmanGameEntity
     */
    HangmanGame getHangmanGame();
}
