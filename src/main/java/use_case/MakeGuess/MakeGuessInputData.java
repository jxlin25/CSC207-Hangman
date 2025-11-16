package use_case.MakeGuess;

import entity.Guess;

public class MakeGuessInputData {
    private final Guess guess;

    public MakeGuessInputData(Guess guess) {
        this.guess = guess;
    }
    public Guess getGuess() {
        return guess;
    }
}
