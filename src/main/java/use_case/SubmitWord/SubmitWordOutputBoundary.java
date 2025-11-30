package use_case.SubmitWord;

public interface SubmitWordOutputBoundary {
    void prepareSuccessView(SubmitWordOutputData outputData);
    void prepareFailView(String error);
}
