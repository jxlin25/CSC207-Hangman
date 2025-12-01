package use_case.Stats;

import entity.GameStats;



/**
 * Interactor for handling statistics use cases.
 */
public class StatsInteractor implements StatsInputBoundary {
    private final StatsDataAccessInterface statsDataAccess;
    private final StatsOutputBoundary statsPresenter;

    public StatsInteractor(StatsDataAccessInterface statsDataAccess,
                           StatsOutputBoundary statsPresenter) {
        this.statsDataAccess = statsDataAccess;
        this.statsPresenter = statsPresenter;
    }

    @Override
    public void execute() {
        GameStats stats = statsDataAccess.loadStatistics();
        statsPresenter.presentStatistics(stats);
    }
}