package interface_adapter.stats;

import use_case.stats.StatsInputBoundary;

/**
 * Controller for statistics.
 */
public class StatsController {
    private final StatsInputBoundary statsUseCaseInteractor;

    public StatsController(StatsInputBoundary statsUseCaseInteractor) {
        this.statsUseCaseInteractor = statsUseCaseInteractor;
    }

    /**
     * Execute for statistics.
     */
    public void execute() {
        statsUseCaseInteractor.execute();
    }
}
