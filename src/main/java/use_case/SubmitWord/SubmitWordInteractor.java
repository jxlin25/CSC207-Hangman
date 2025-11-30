package use_case.SubmitWord;

import data_access.SubmitWordDataAccessInterface;

public class SubmitWordInteractor implements SubmitWordInputBoundary {
    private final SubmitWordDataAccessInterface dataAccess;
    private final SubmitWordOutputBoundary outputBoundary;

    public SubmitWordInteractor(SubmitWordDataAccessInterface dataAccess, SubmitWordOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(SubmitWordInputData inputData) {
        try {
            // Send word to server
            dataAccess.submitWord(inputData.getRoomId(), inputData.getWord());

            // Local optimistic update if needed
            SubmitWordOutputData outputData = new SubmitWordOutputData(true, "Word submitted successfully");
            outputBoundary.prepareSuccessView(outputData);
        } catch (Exception e) {
            outputBoundary.prepareFailView(e.getMessage());
        }
    }
}
