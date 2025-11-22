package use_case.MakeGuess;

public interface MakeGuessInputBoundary {

    /**
     * Executes the MakeGuess use case.
     *
     * @param makeGuessInputData the input data that is required for the interactor to process
     */
    void execute(MakeGuessInputData makeGuessInputData);
}
