package interface_adapter.stats;

public class SessionStatistics {
    private int roundsPlayed;
    private int roundsWon;
    private int roundsLost;
    private int totalAttemptsUsed;
    private long totalMillisPlayed;

    public void recordRoundWin(int attemptsUsed, long millisInRound) {
        roundsPlayed++;
        roundsWon++;
        totalAttemptsUsed += Math.max(0, attemptsUsed);
        totalMillisPlayed += Math.max(0, millisInRound);
    }

    public void recordRoundLoss(int attemptsUsed, long millisInRound) {
        roundsPlayed++;
        roundsLost++;
        totalAttemptsUsed += Math.max(0, attemptsUsed);
        totalMillisPlayed += Math.max(0, millisInRound);
    }

    public int getRoundsPlayed() { return roundsPlayed; }
    public int getRoundsWon() { return roundsWon; }
    public int getRoundsLost() { return roundsLost; }
    public int getTotalAttemptsUsed() { return totalAttemptsUsed; }
    public double getAverageAttemptsPerRound() {
        return roundsPlayed == 0 ? 0.0 : (double) totalAttemptsUsed / roundsPlayed;
    }
    public long getTotalMillisPlayed() { return totalMillisPlayed; }
}
