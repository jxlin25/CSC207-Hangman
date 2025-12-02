package interface_adapter.stats;

import entity.GameStats;
import use_case.stats.StatsOutputBoundary;

/**
 * Presenter for statistics.
 */
public class StatsPresenter implements StatsOutputBoundary {
    private final StatsViewModel statsViewModel;

    public StatsPresenter(StatsViewModel statsViewModel) {
        this.statsViewModel = statsViewModel;
    }

    @Override
    public void presentStatistics(GameStats stats) {
        final StatsState state = statsViewModel.getState();
        state.setStats(stats);
        statsViewModel.setState(state);
        statsViewModel.firePropertyChange();
    }
}
