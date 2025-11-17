package use_case.MakeGuess;

import entity.Guess;
import entity.WordPuzzle;

public interface MakeGuessWordPuzzleDataAccessInterface {

    // Get the current word puzzle
    WordPuzzle getCurrentWordPuzzle();

    boolean isGuessCorrect(Guess guess);

    void revealLetter(Guess guess);

    boolean isPuzzleComplete();

}