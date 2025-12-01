package use_case.Stats;

import entity.GameStats;

public interface StatsDataAccessInterface {
    void saveStatistics(GameStats stats);
    GameStats loadStatistics();
}