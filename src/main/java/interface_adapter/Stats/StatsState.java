package interface_adapter.Stats;

import entity.GameStats;

/**
 * State for statistics view
 */
public class StatsState {
    private GameStats stats;

    public StatsState() {}

    public StatsState(StatsState copy) {
        this.stats = copy.stats;
    }

    public GameStats getStats() { return stats; }
    public void setStats(GameStats stats) { this.stats = stats; }
}