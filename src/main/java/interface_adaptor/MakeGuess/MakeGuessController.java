package interface_adaptor;

import entity.Guess;
import use_case.MakeGuess.MakeGuessInputBoundary;
import use_case.MakeGuess.MakeGuessInputData;
import use_case.MakeGuess.MakeGuessInteractor;

public class MakeGuessController {

    private final MakeGuessInputBoundary makeGuessInputBoundary;

    public MakeGuessController(MakeGuessInputBoundary makeGuessInputBoundary) {
        this.makeGuessInputBoundary = makeGuessInputBoundary;
    }

    public void execute(Guess guess) {
        final MakeGuessInputData makeGuessInputData = new MakeGuessInputData(guess);

        this.makeGuessInputBoundary.execute(makeGuessInputData);
    }
}
