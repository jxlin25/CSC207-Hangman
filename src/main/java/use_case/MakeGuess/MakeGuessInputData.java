package use_case.MakeGuess;

import entity.Guess;

public class MakeGuessInputData {

    final  private Guess guess;
    public MakeGuessInputData(Guess guess) {
        this.guess = guess;
    }
    public Guess getGuess() {
        return guess;
    }
}
