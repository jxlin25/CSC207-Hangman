package use_case.MakeGuess;

import entity.Guess;
import entity.HangmanGame;
import entity.Round;

public interface MakeGuessHangmanGameDataAccessInterface {

    HangmanGame getHangmanGame();

    Round getCurrentRound();

    void decreaseCurrentRoundAttempt();

    void addGuessToCurrentRound(Guess guess);

    boolean setCurrentRoundWonAndStartNextRound();

    boolean setCurrentRoundLostAndStartNextRound();

    int getCurrentRoundAttempt();

}
