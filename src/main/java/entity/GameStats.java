package entity;

public class GameStats {
    private int wins;
    private int losses;

    public GameStats() {
        this.wins = 0;
        this.losses = 0;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public void incrementWins() {
        wins++;
    }

    public void incrementLosses() {
        losses++;
    }

    public int getTotalGames() {
        return wins + losses;
    }

    public double getWinRate() {
        return getTotalGames() > 0 ? (wins * 100.0) / getTotalGames() : 0.0;
    }

    // For persistence, we might want to set the wins and losses as well
    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }
}