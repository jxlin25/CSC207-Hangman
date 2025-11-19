package use_case.InitializeFirstRound;

import entity.Round;
import entity.WordPuzzle;

public interface InitializeFirstRoundDataAccessInterface {

    Round getFirstRound();

    String getFirstMaskedWord();

    int getFirstRoundNumber();

    int getFirstRoundAttempt();

    WordPuzzle getFirstWordPuzzle();

}
