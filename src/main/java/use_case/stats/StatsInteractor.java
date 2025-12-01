package use_case.stats;
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

    /**
     * Execution function.
     */
    public void execute() {
        final GameStats stats = statsDataAccess.loadStatistics();
        statsPresenter.presentStatistics(stats);
    }
}
