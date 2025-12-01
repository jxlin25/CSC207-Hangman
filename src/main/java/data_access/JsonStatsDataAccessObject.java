package data_access;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.GameStats;
import use_case.stats.StatsDataAccessInterface;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * JSON-based persistence for game statistics
 */
public class JsonStatsDataAccessObject implements StatsDataAccessInterface {
    private final Path filePath;
    private final Gson gson;

    public JsonStatsDataAccessObject(String jsonFilePath) {
        this.filePath = Paths.get(jsonFilePath);
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        ensureStatsFileExists();
    }

    private void ensureStatsFileExists() {
        if (!Files.exists(filePath)) {
            GameStats defaultStats = new GameStats();
            saveStatistics(defaultStats);
        }
    }
    @Override
    public GameStats loadStatistics() {
        if (!Files.exists(filePath)) {
            return new GameStats();
        }
        try (Reader reader = new FileReader(filePath.toFile())) {
            GameStats stats = gson.fromJson(reader, GameStats.class);
            return stats != null ? stats : new GameStats();
        } catch (IOException e) {
            System.err.println("Error loading statistics from " + filePath + ": " + e.getMessage());
            return new GameStats();
        }
    }

    @Override
    public void saveStatistics(GameStats stats) {
        try (Writer writer = new FileWriter(filePath.toFile())) {
            gson.toJson(stats, writer);
        } catch (IOException e) {
            System.err.println("Error saving statistics to " + filePath + ": " + e.getMessage());
        }
    }
}