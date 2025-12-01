package use_case.Stats;

import entity.GameStats;

public interface StatsDataAccessInterface {
    void saveStats(GameStats stats);
    GameStats loadStatistics();
}