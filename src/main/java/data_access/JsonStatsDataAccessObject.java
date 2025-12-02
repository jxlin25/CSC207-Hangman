package data_access;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.GameStats;
import use_case.stats.StatsDataAccessInterface;

/**
 * JSON-based persistence for game statistics.
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
            final GameStats defaultStats = new GameStats();
            saveStatistics(defaultStats);
        }
    }

    @Override
    public GameStats loadStatistics() {
        GameStats result = new GameStats();

        try {
            if (Files.exists(filePath)) {
                try (Reader reader = new FileReader(filePath.toFile())) {
                    final GameStats loaded = gson.fromJson(reader, GameStats.class);

                    if (loaded != null) {
                        result = loaded;
                    }
                }
            }
        }
        catch (IOException error) {
            System.err.println("Error loading statistics from " + filePath + ": " + error.getMessage());
        }

        return result;
    }

    @Override
    public void saveStatistics(GameStats stats) {
        try (Writer writer = new FileWriter(filePath.toFile())) {
            gson.toJson(stats, writer);
        }
        catch (IOException error) {
            System.err.println("Error saving statistics to " + filePath + ": " + error.getMessage());
        }
    }
}
