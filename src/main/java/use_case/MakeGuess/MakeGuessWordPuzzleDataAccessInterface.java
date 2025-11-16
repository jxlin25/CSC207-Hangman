package use_case.MakeGuess;

import entity.Guess;
import entity.WordPuzzle;

public interface MakeGuessPuzzleWordDataAccessInterface {

    // Get the current word puzzle
    WordPuzzle getCurrentWordPuzzle();

    boolean isGuessCorrect(Guess guess);

    void revealLetter(char letter);

    void save(WordPuzzle wordPuzzle);

}