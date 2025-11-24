package stats;

public class InMemorySessionStatisticsRepository {
    private final SessionStatistics stats = new SessionStatistics();
    public SessionStatistics getStats() { return stats; }
}
