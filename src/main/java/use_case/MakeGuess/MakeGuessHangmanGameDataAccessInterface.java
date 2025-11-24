package use_case.MakeGuess;

import entity.Guess;
import entity.HangmanGame;
import entity.Round;
import entity.WordPuzzle;

/**
 * The data access interface for the MakeGuess use case.
 */
public interface MakeGuessHangmanGameDataAccessInterface {

    /**
     * Gets the HangmanGame entity.
     * @return the HangmanGame object
     */
    HangmanGame getHangmanGame();

    /**
     * Gets the current Round object in the HangmanGame.
     * @return the current Round object in the HangmanGame
     */
    Round getCurrentRound();

    /**
     * Updates the HangmanGame entity.
     * @param hangmanGame new entity of the HangmanGame
     */
    void setHangmanGame(HangmanGame hangmanGame);

    /**
     * Decreases the remaining attempts of the current Round.
     */
    void decreaseCurrentRoundAttempt();

    /**
     * Appends the ArrayList of Guess in the current Round object by a new Guess.
     * @param guess the new Guess object
     */
    void addGuessToCurrentRound(Guess guess);

    /**
     * Updates the status of the current Round as WON and set the next Round object as the current Round.
     * @return true if successfully moved to the next round; false if all rounds are over
     */
    boolean setCurrentRoundWonAndStartNextRound();

    /**
     * Updates the status of the current Round as LOST and set the next Round object as the current Round.
     * @return true if successfully moved to the next round; false if all rounds are over
     */
    boolean setCurrentRoundLostAndStartNextRound();

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
     * Gets the WordPuzzle object of the current Round.
     * @return the WordPuzzle object of the current Round
     */
    WordPuzzle getCurrentWordPuzzle();

    /**
     * Checks if a guess is correct to the current WordPuzzle object.
     * @param guess Guess object that is going to be assessed
     * @return true if the guess is correct to the current WordPuzzle object; otherwise false
     */
    boolean isGuessCorrectToCurrentWordPuzzle(Guess guess);

    /**
     * Marks all the occurrences of the letter in a Guess object in the current WordPuzzle object as unrevealed.
     * @param guess Guess object that is going to be assessed
     */
    void revealLetterInCurrentWordPuzzle(Guess guess);

    /**
     * Checks if the current WordPuzzle isComplete.
     * @return true if the current WordPuzzle is marked as complete; otherwise false
     */
    boolean isCurrentWordPuzzleComplete();

    /**
     * Gets the string of the masked word of the current WordPuzzle.
     * @return the string of the masked word of the current WordPuzzle
     */
    String getMaskedWord();

    /**
     * Gets the array of Chars of the word in the current WordPuzzle.
     * @return the array of Char of the word in the current WordPuzzle
     */
    char[] getCurrentWordPuzzleLetters();

    /**
     * Gets the array of booleans in the current WordPuzzle that marks whether each letter is revealed/hidden.
     * @return the array of booleans in the current WordPuzzle that marks whether each letter is revealed/hidden
     */
    boolean[] getCurrentWordPuzzleRevealedLettersBooleans();

}