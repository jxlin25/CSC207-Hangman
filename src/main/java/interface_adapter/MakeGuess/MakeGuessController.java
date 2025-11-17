package interface_adapter.MakeGuess;

import entity.Guess;
import use_case.MakeGuess.MakeGuessInputBoundary;
import use_case.MakeGuess.MakeGuessInputData;
import use_case.MakeGuess.MakeGuessInteractor;

public class MakeGuessController {

    private final MakeGuessInputBoundary makeGuessInputBoundary;

    public MakeGuessController(MakeGuessInputBoundary makeGuessInputBoundary) {
        this.makeGuessInputBoundary = makeGuessInputBoundary;
    }

    public void execute(String letter) {
        // 1. Check for valid input
        if (letter == null || letter.isEmpty()) {
            return;
        }

        Guess guess = new Guess(letter.charAt(0));

        final MakeGuessInputData makeGuessInputData = new MakeGuessInputData(guess);
        this.makeGuessInputBoundary.execute(makeGuessInputData);
    }
}
