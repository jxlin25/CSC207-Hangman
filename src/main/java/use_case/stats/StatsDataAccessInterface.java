package use_case.stats;

import entity.GameStats;

/**
 * Interface for accessing stats data.
 */
public interface StatsDataAccessInterface {

    /**
     * Saves the given game statistics.
     *
     * @param stats the statistics to save
     */
    void saveStatistics(GameStats stats);

    /**
     * Loads and returns the game statistics.
     *
     * @return the loaded game statistics
     */
    GameStats loadStatistics();
}
