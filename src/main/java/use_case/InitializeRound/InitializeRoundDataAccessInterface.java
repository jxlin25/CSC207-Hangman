package use_case.InitializeRound;

import entity.Round;
import entity.WordPuzzle;

public interface InitializeRoundDataAccessInterface {

    /**
     * Gets the first Round object in the rounds of HangmanGame.
     * @return the first Round object in the rounds of HangmanGame
     */
    Round getFirstRound();

    /**
     * Gets the masked word puzzle from the first Round object.
     * @return the masked string of word
     */
    String getFirstMaskedWord();

    /**
     * Returns the round number of the first round in the HangmanGame.
     * @return the round number of the first round in the HangmanGame
     */
    int getFirstRoundNumber();

    /**
     * Gets the remaining attempts of the first Round.
     * @return the remaining attempts of the first Round
     */
    int getFirstRoundAttempt();

    /**
     * Gets the WordPuzzle object of the first Round.
     * @return the WordPuzzle object of the first Round
     */
    WordPuzzle getFirstWordPuzzle();

}