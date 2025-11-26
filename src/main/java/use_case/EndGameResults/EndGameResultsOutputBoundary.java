// use_case/ResultFetch/ResultFetchOutputBoundary.java

package use_case.EndGameResults;

public interface EndGameResultsOutputBoundary {
    // The presenter will receive the three calculated results directly
    void present(String finalStatus, String finalWord, int attemptsTaken);
}