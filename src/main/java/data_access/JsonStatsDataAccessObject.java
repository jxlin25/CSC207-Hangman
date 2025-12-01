package data_access;

import entity.GameStats;
import use_case.Stats.StatsDataAccessInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

/**
 * JSON-based persistence for game statistics
 */
public class JsonStatsDataAccessObject implements StatsDataAccessInterface {
    private static final String STATS_FILE = "hangman_stats.json";
    private final Gson gson;

    public JsonStatsDataAccessObject() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        ensureStatsFileExists();
    }

    private void ensureStatsFileExists() {
        File file = new File(STATS_FILE);
        if (!file.exists()) {
            GameStats defaultStats = new GameStats();
            saveStats(defaultStats);
        }
    }
    @Override
    public GameStats loadStatistics() {
        try (Reader reader = new FileReader(STATS_FILE)) {
            return gson.fromJson(reader, GameStats.class);
        } catch (IOException e) {
            System.err.println("Error loading statistics: " + e.getMessage());
            return new GameStats();
        }
    }

    @Override
    public void saveStats(GameStats stats) {
        try (Writer writer = new FileWriter(STATS_FILE)) {
            gson.toJson(stats, writer);
        } catch (IOException e) {
            System.err.println("Error saving statistics: " + e.getMessage());
        }
    }

    public void updateStats(boolean gameWon) {
        GameStats currentStats = loadStatistics();
        if (gameWon) {
            currentStats.incrementWins();
        } else {
            currentStats.incrementLosses();
        }
        saveStats(currentStats);
    }
}