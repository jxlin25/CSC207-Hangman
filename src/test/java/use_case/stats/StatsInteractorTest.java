
package use_case.stats;

import entity.GameStats;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class StatsInteractorTest {

    private StatsDataAccessInterface statsDataAccessObject;
    private StatsOutputBoundary statsPresenter;
    private StatsInteractor statsInteractor;

    // A simple mock for StatsDataAccessInterface
    private static class MockStatsDataAccessObject implements StatsDataAccessInterface {
        private GameStats gameStatsToReturn;
        private boolean loadStatisticsCalled = false;

        public void setGameStatsToReturn(GameStats gameStatsToReturn) {
            this.gameStatsToReturn = gameStatsToReturn;
        }

        @Override
        public void saveStatistics(GameStats stats) {
            // Not used in this interactor's test, but required by interface
        }

        @Override
        public GameStats loadStatistics() {
            loadStatisticsCalled = true;
            return gameStatsToReturn;
        }

        public boolean isLoadStatisticsCalled() {
            return loadStatisticsCalled;
        }
    }

    // A simple mock for StatsOutputBoundary
    private static class MockStatsPresenter implements StatsOutputBoundary {
        private GameStats presentedStatistics;
        private boolean presentStatisticsCalled = false;

        @Override
        public void presentStatistics(GameStats statistics) {
            this.presentedStatistics = statistics;
            this.presentStatisticsCalled = true;
        }

        public GameStats getPresentedStatistics() {
            return presentedStatistics;
        }

        public boolean isPresentStatisticsCalled() {
            return presentStatisticsCalled;
        }
    }

    @Before
    public void setUp() {
        // Initialize mocks before each test
        // This is primarily for clarity, individual tests will often set specific behaviors.
        statsDataAccessObject = new MockStatsDataAccessObject();
        statsPresenter = new MockStatsPresenter();
        statsInteractor = new StatsInteractor(statsDataAccessObject, statsPresenter);
    }

    @Test
    public void testExecuteSuccess() {
        // ARRANGE
        // Configure the mock DAO to return specific stats
        GameStats expectedStats = new GameStats();
        expectedStats.setRoundsWon(10);
        expectedStats.setRoundsLost(5);
        ((MockStatsDataAccessObject) statsDataAccessObject).setGameStatsToReturn(expectedStats);

        // ACT
        statsInteractor.execute(); // No input data for this interactor's execute method

        // ASSERT
        // Verify that the DAO's loadStatistics was called
        assertTrue("loadStatistics() should have been called on the DAO",
                ((MockStatsDataAccessObject) statsDataAccessObject).isLoadStatisticsCalled());

        // Verify that the presenter's presentStatistics was called
        assertTrue("presentStatistics() should have been called on the presenter",
                ((MockStatsPresenter) statsPresenter).isPresentStatisticsCalled());

        // Verify that the correct GameStats were passed to the presenter
        GameStats actualStats = ((MockStatsPresenter) statsPresenter).getPresentedStatistics();
        assertNotNull("Presented statistics should not be null", actualStats);
        assertEquals("Presented rounds won should match expected", expectedStats.getRoundsWon(), actualStats.getRoundsWon());
        assertEquals("Presented rounds lost should match expected", expectedStats.getRoundsLost(), actualStats.getRoundsLost());
    }

}
