package interface_adapter.Stats;

import use_case.Stats.StatsOutputBoundary;
import entity.GameStats;

/**
 * Presenter for statistics
 */
public class StatsPresenter implements StatsOutputBoundary {
    private final StatsViewModel statsViewModel;

    public StatsPresenter(StatsViewModel statsViewModel) {
        this.statsViewModel = statsViewModel;
    }

    @Override
    public void presentStatistics(GameStats stats) {
        StatsState state = statsViewModel.getState();
        state.setStats(stats);
        statsViewModel.setState(state);
        statsViewModel.firePropertyChange();
    }
}