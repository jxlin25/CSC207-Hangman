package entity.game_session;

import entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

public class GameState {

    private final int roomId;
    private final List<Player> players;
    private final int maxIncorrectGuesses = 6;
    private final int maxRounds = 3;

    private Map<String, Integer> scores;
    private int currentRound;

    private GamePhase currentPhase;
    private String currentWordSetterId;
    private String currentGuesserId;

    private String secretWord;
    private String customHint;
    private String revealedWord;
    private Set<Character> guessedLetters;
    private int incorrectGuessesCount;
    private String roundWinnerId;

    public GameState(int roomId, List<Player> players) {
        this.roomId = roomId;
        this.players = players;

        this.scores = new HashMap<>();
        for (Player p : players) {
            this.scores.put(p.getId(), 0);
        }
        this.currentRound = 1;

        this.currentPhase = GamePhase.WORD_SELECTION;
        this.currentWordSetterId = players.getFirst().getId();
        this.currentGuesserId = players.get(1).getId();

        this.secretWord = null;
        this.customHint = null;
        this.revealedWord = "";
        this.guessedLetters = new HashSet<>();
        this.incorrectGuessesCount = 0;
        this.roundWinnerId = null;
    }

    public void initializeForGuessing(String secretWord) {
        this.secretWord = secretWord.toUpperCase();
        this.revealedWord = "_".repeat(secretWord.length());
        this.guessedLetters = new HashSet<>();
        this.incorrectGuessesCount = 0;
        this.currentPhase = GamePhase.GUESSING;
    }

    public GamePhase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(GamePhase currentPhase) {
        this.currentPhase = currentPhase;
    }

    public String getCurrentGuesserId() {
        return currentGuesserId;
    }

    public void setCurrentGuesserId(String currentGuesserId) {
        this.currentGuesserId = currentGuesserId;
    }

    public void setSecretWord(String secretWord) {this.secretWord = secretWord;}

    public void setGuessedLetters(Set<Character> guessedLetters) {
        this.guessedLetters = guessedLetters;
    }

    public int getRoomId() {
        return roomId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getMaxIncorrectGuesses() {
        return maxIncorrectGuesses;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public String getCurrentWordSetterId() {
        return currentWordSetterId;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public String getCustomHint() {
        return customHint;
    }

    public String getRevealedWord() {
        return revealedWord;
    }

    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    public int getIncorrectGuessesCount() {
        return incorrectGuessesCount;
    }

    public boolean isWordValid(String word) {
        return word != null && word.matches("[a-zA-Z]+") && word.length() >= 3;
    }

    public void setIncorrectGuessesCount(int count) {
        this.incorrectGuessesCount = count;
    }

    public String getRoundWinnerId() {
        return roundWinnerId;
    }
    public int getMaxRounds() {
        return maxRounds;
    }

    public void setRevealedWord(String revealedWord) {
        this.revealedWord = revealedWord;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }
    public void setCurrentWordSetterId(String currentWordSetterId) {
        this.currentWordSetterId = currentWordSetterId;
    }

    public void setRoundWinnerId(String roundWinnerId) {
        this.roundWinnerId = roundWinnerId;
    }
}
