package entity;

public class GameStats {
    private int roundsWon;
    private int roundsLost;

    public GameStats() {
        this.roundsWon = 0;
        this.roundsLost = 0;
    }

    public int getRoundsWon() {
        return roundsWon;
    }

    public int getRoundsLost() {
        return roundsLost;
    }

    public void incrementRoundsWon() {
        roundsWon++;
    }

    public void incrementRoundsLost() {
        roundsLost++;
    }

    public int getTotalRounds() {
        return roundsWon + roundsLost;
    }

    public double getRoundWinRate() {
        return getTotalRounds() > 0 ? (roundsWon * 100.0) / getTotalRounds() : 0.0;
    }

    // For persistence, we might want to set the wins and losses as well
    public void setRoundsWon(int roundsWon) {
        this.roundsWon = roundsWon;
    }

    public void setRoundsLost(int roundsLost) {
        this.roundsLost = roundsLost;
    }
}