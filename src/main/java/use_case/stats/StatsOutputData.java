package use_case.stats;

import entity.GameStats;

/**
 * Output Data for Statistics use case.
 */
public class StatsOutputData {
    private final GameStats statistics;
    private final boolean success;
    private final String errorMessage;

    public StatsOutputData(GameStats statistics) {
        this.statistics = statistics;
        this.success = true;
        this.errorMessage = null;
    }

    public StatsOutputData(String errorMessage) {
        this.statistics = null;
        this.success = false;
        this.errorMessage = errorMessage;
    }

    // Getters
    public GameStats getStatistics() {
        return statistics;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
