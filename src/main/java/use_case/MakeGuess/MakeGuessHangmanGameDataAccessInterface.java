package use_case.MakeGuess;

import entity.Guess;
import entity.HangmanGame;
import entity.Round;
import entity.WordPuzzle;

/**
 * The data access interface for the MakeGuess use case.
 */
public interface MakeGuessHangmanGameDataAccessInterface {

    HangmanGame getHangmanGame();

    Round getCurrentRound();

    void decreaseCurrentRoundAttempt();

    void addGuessToCurrentRound(Guess guess);

    boolean setCurrentRoundWonAndStartNextRound();

    boolean setCurrentRoundLostAndStartNextRound();

    int getCurrentRoundAttempt();

    // Get the current word puzzle
    WordPuzzle getCurrentWordPuzzle();

    boolean isGuessCorrect(Guess guess);

    void revealLetter(Guess guess);

    boolean isPuzzleComplete();

    char[] getCurrentWordPuzzleLetters();

    boolean[] getCurrentWordPuzzleRevealedLettersBooleans();

    String getMaskedWord();

    int getCurrentRoundNumber();

}
