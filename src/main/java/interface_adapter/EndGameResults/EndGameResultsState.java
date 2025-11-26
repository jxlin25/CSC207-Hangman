package interface_adapter.EndGameResults;

public class EndGameResultsState {
    private String finalStatus = "";
    private String finalWord = "";
    private int attemptsTaken = 0;

    // Default constructor
    public EndGameResultsState() {
    }

    // Getters
    public String getFinalStatus() {
        return finalStatus;
    }

    public String getFinalWord() {
        return finalWord;
    }

    public int getAttemptsTaken() {
        return attemptsTaken;
    }

    // Setters
    public void setFinalStatus(String finalStatus) {
        this.finalStatus = finalStatus;
    }

    public void setFinalWord(String finalWord) {
        this.finalWord = finalWord;
    }

    public void setAttemptsTaken(int attemptsTaken) {
        this.attemptsTaken = attemptsTaken;
    }
}