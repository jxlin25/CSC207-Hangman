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

    /**
     * Executes the Make Guess use case.
     * @param letter the letter that the player guessed
     */
    public void execute(char letter) {

        final Guess guess = new Guess(letter);

        final MakeGuessInputData makeGuessInputData = new MakeGuessInputData(guess);
        this.makeGuessInputBoundary.execute(makeGuessInputData);
    }
}
