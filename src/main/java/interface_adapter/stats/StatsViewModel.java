package interface_adapter.stats;

import interface_adapter.ViewModel;

/**
 * ViewModel for statistics.
 */
public class StatsViewModel extends ViewModel {
    private StatsState state = new StatsState();

    public StatsViewModel() {
        super("stats");
    }

    public StatsState getState() {
        return state;
    }

    public void setState(StatsState state) {
        this.state = state;
    }
}
