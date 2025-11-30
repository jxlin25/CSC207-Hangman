package interface_adapter.make_guess;

import entity.Guess;
import use_case.make_guess.MakeGuessInputBoundary;
import use_case.make_guess.MakeGuessInputData;

/** The Controller for the MakeGuess use case.
 */
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
