package use_case.stats;

import entity.GameStats;

public interface StatsOutputBoundary {
    void presentStatistics(GameStats statistics);
}
