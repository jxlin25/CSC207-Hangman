package use_case.MakeGuess;

public interface MakeGuessOutputBoundary {

    /**
     * Updates the view of the GUI.
     *
     * @param outputData the output data that is required for updating the GUI
     */
    void updateView(MakeGuessOutputData outputData);
}
