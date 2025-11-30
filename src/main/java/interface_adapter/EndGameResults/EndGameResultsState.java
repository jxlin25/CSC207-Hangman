package interface_adapter.EndGameResults;

import java.util.ArrayList;
import java.util.List;

public class EndGameResultsState {

    private String finalStatus = "";
    private List<RoundResult> roundResults = new ArrayList<>();

    public EndGameResultsState() {
    }

    public String getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(String finalStatus) {
        this.finalStatus = finalStatus;
    }

    public List<RoundResult> getRoundResults() {
        return roundResults;
    }

    public void setRoundResults(List<RoundResult> roundResults) {
        this.roundResults = roundResults;
    }

    // Inner class to represent a single round's data
    public static class RoundResult {
        private final int roundNumber;
        private final String word;
        private final int attemptsUsed;
        private final String status;

        public RoundResult(int roundNumber, String word, int attemptsUsed, String status) {
            this.roundNumber = roundNumber;
            this.word = word;
            this.attemptsUsed = attemptsUsed;
            this.status = status;
        }

        public int getRoundNumber() {
            return roundNumber;
        }

        public String getWord() {
            return word;
        }

        public int getAttemptsUsed() {
            return attemptsUsed;
        }

        public String getStatus() {
            return status;
        }
    }
}
