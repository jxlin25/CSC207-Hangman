package data_access;

import use_case.GenerateWord.WordPuzzleDataAccessInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class DBGenerateWordDataAccessObject implements WordPuzzleDataAccessInterface {
    public enum Difficulty { EASY, MEDIUM, HARD }

    private Difficulty difficulty = Difficulty.MEDIUM;

    public DBGenerateWordDataAccessObject() {}
    public DBGenerateWordDataAccessObject(Difficulty difficulty) {
        if (difficulty != null) this.difficulty = difficulty;
    }
    public void setDifficulty(Difficulty difficulty) {
        if (difficulty != null) this.difficulty = difficulty;
    }

    @Override
    public String getRandomWord() {
        for (int i = 0; i < 5; i++) {
            String w = fetchRandomWordByDifficulty();
            if (w != null && isAlphabetic(w) && isValidWord(w)) {
                return w.toLowerCase(Locale.ROOT);
            }
        }
        return "apple";
    }

    @Override
    public boolean isValidWord(String word) {
        if (word == null || word.isBlank()) return false;
        String w = word.trim().toLowerCase(Locale.ROOT);
        if (!isAlphabetic(w)) return false;
        HttpURLConnection conn = null;
        try {
            URL url = new URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + w);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3500);
            conn.setReadTimeout(3500);
            int code = conn.getResponseCode();
            return code >= 200 && code < 300;
        } catch (IOException e) {
            // If dictionary API down, allow to not block class usage
            return true;
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    private String fetchRandomWordByDifficulty() {
        int minLen, maxLen;
        switch (difficulty) {
            case EASY:   minLen = 4;  maxLen = 6;  break;
            case MEDIUM: minLen = 6;  maxLen = 8;  break;
            case HARD:   minLen = 8;  maxLen = 12; break;
            default:     minLen = 6;  maxLen = 8;
        }
        String endpoint = "https://random-word-api.herokuapp.com/word?number=1&minLength=" +
                minLen + "&maxLength=" + maxLen;
        String json = httpGet(endpoint);
        if (json == null || json.isBlank()) return null;
        return extractFromArray(json);
    }

    private String httpGet(String endpoint) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(endpoint);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3500);
            conn.setReadTimeout(3500);
            int code = conn.getResponseCode();
            if (code < 200 || code >= 300) return null;

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) sb.append(line);
                return sb.toString();
            }
        } catch (IOException e) {
            return null;
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    private String extractFromArray(String json) {
        String t = json.trim();
        if (!t.startsWith("[") || !t.endsWith("]")) return null;
        t = t.substring(1, t.length() - 1).trim();
        if (t.startsWith("\"") && t.endsWith("\"") && t.length() >= 2) {
            t = t.substring(1, t.length() - 1);
        }
        return t;
    }

    private boolean isAlphabetic(String s) {
        for (int i = 0; i < s.length(); i++) if (!Character.isAlphabetic(s.charAt(i))) return false;
        return true;
    }
}
