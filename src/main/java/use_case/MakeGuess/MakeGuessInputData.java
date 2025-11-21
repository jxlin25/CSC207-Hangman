package use_case.MakeGuess;

import entity.Guess;

/**
 * Class representing input data of the Make Guess use case.
 */
public final class MakeGuessInputData {

    private final Guess guess;

    public MakeGuessInputData(Guess guess) {
        this.guess = guess;
    }

    public Guess getGuess() {
        return guess;
    }

}
