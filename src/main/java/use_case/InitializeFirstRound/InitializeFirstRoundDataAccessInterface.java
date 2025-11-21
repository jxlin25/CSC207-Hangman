package use_case.InitializeFirstRound;

import entity.Round;
import entity.WordPuzzle;

public interface InitializeFirstRoundDataAccessInterface {

    /**
     * Gets the first Round object in the HangmanGame.rounds.
     * @return the first Round object in the HangmanGame.rounds
     */
    Round getFirstRound();

    /**
     * Gets the masked word puzzle from the first Round object.
     * @return the masked string of word
     */
    String getFirstMaskedWord();

    /**
     * Returns the first Round object in the HangmanGame.
     * @return the round number of the first Round
     */
    int getFirstRoundNumber();

    int getFirstRoundAttempt();

    WordPuzzle getFirstWordPuzzle();

}
