package interface_adapter.MakeGuess;

import entity.Guess;
import use_case.MakeGuess.MakeGuessInputBoundary;
import use_case.MakeGuess.MakeGuessInputData;
import use_case.MakeGuess.MakeGuessInteractor;

/** The Controller for the MakeGuess use case.
 */
public class MakeGuessController {

    private final MakeGuessInputBoundary makeGuessInputBoundary;

    public MakeGuessController(MakeGuessInputBoundary makeGuessInputBoundary) {
        this.makeGuessInputBoundary = makeGuessInputBoundary;
    }

    public void execute(char letter) {

        Guess guess = new Guess(letter);

        final MakeGuessInputData makeGuessInputData = new MakeGuessInputData(guess);
        this.makeGuessInputBoundary.execute(makeGuessInputData);
    }
}
