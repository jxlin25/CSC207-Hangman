package use_case.Stats;

import entity.GameStats;

public interface StatsOutputBoundary {
    void presentStatistics(GameStats statistics);
}
