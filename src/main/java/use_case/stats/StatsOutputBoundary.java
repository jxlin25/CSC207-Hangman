package use_case.stats;

import entity.GameStats;

/**
 * Interface defining how to present game statistics.
 */
public interface StatsOutputBoundary {

    /**
     * Presents the given game statistics.
     *
     * @param statistics the statistics to present
     */
    void presentStatistics(GameStats statistics);
}
