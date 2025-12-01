package use_case.stats;

import entity.GameStats;

public interface StatsDataAccessInterface {
    void saveStatistics(GameStats stats);
    GameStats loadStatistics();
}