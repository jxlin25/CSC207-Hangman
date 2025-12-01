package interface_adapter.Stats;

import use_case.Stats.StatsInputBoundary;

/**
 * Controller for statistics
 */
public class StatsController {
    private final StatsInputBoundary statsUseCaseInteractor;

    public StatsController(StatsInputBoundary statsUseCaseInteractor) {
        this.statsUseCaseInteractor = statsUseCaseInteractor;
    }

    public void execute() {
        statsUseCaseInteractor.execute();
    }
}