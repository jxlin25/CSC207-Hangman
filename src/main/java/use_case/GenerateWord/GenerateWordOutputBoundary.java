package use_case.GenerateWord;

public interface GenerateWordOutputBoundary {
    void prepareSuccessView(GenerateWordOutputData outputData);
    void prepareFailView(String error);
}