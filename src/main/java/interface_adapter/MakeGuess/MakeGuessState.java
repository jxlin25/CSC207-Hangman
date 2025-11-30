package interface_adapter.MakeGuess;

import entity.game_session.GamePhase;
import static Constant.StatusConstant.*;
import java.util.HashSet;
import java.util.Set;

public class MakeGuessState {

    private String guessedLetter = "";
    private boolean isGuessCorrect =  false;
    private String roundStatus = GUESSING;
    private boolean isGameOver = false;
    private int remainingAttempts = 6;
    private int currentRoundNumber = 1;
    private String maskedWord = "";
    private String message = "";

    // Multiplayer related fields
    private String currentWordSetterId = null;
    private String currentGuesserId = null;
    private String secretWord = null;
    private int maxIncorrectGuesses = 6;
    private String currentClientId = null;
    private Set<Character> guessedLetters = new HashSet<>();
    private boolean isMultiplayerGame = false;
    private int roomId; // Added roomId field


    public MakeGuessState() {
    }

    public MakeGuessState(String guessedLetter, boolean isGuessCorrect, boolean isGameOver, String roundStatus, int remainingAttempts, int currentRoundNumber,  String maskedWord) {
        this.guessedLetter = guessedLetter;
        this.isGuessCorrect = isGuessCorrect;
        this.isGameOver = isGameOver;
        this.roundStatus = roundStatus;
        this.remainingAttempts = remainingAttempts;
        this.currentRoundNumber = currentRoundNumber;
        this.maskedWord = maskedWord;
    }

    // Existing Getters and Setters
    public String getGuessedLetter() { return guessedLetter; }
    public void setGuessedLetter(String guessedLetter) { this.guessedLetter = guessedLetter; }

    public boolean isGuessCorrect() { return isGuessCorrect; }
    public void setGuessCorrect(boolean guessCorrect) { this.isGuessCorrect = guessCorrect; }

    public String getRoundStatus() { return this.roundStatus; }
    public void setRoundStatus(String status) { this.roundStatus = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isGameOver() { return isGameOver; }
    public void setGameOver(boolean gameOver) { this.isGameOver = gameOver; }

    public int getRemainingAttempts() { return remainingAttempts; }
    public void setRemainingAttempts(int remainingAttempts) { this.remainingAttempts = remainingAttempts; }

    public int getCurrentRoundNumber() { return currentRoundNumber; }
    public void setCurrentRoundNumber(int currentRoundNumber) { this.currentRoundNumber = currentRoundNumber; }

    public String getMaskedWord() { return maskedWord; }
    public void setMaskedWord(String maskedWord) { this.maskedWord = maskedWord; }


    // New Getters and Setters for multiplayer fields
    public String getCurrentWordSetterId() { return currentWordSetterId; }
    public void setCurrentWordSetterId(String currentWordSetterId) { this.currentWordSetterId = currentWordSetterId; }

    public String getCurrentGuesserId() { return currentGuesserId; }
    public void setCurrentGuesserId(String currentGuesserId) { this.currentGuesserId = currentGuesserId; }

    public String getSecretWord() { return secretWord; }
    public void setSecretWord(String secretWord) { this.secretWord = secretWord; }

    public int getMaxIncorrectGuesses() { return maxIncorrectGuesses; }
    public void setMaxIncorrectGuesses(int maxIncorrectGuesses) { this.maxIncorrectGuesses = maxIncorrectGuesses; }

    public String getCurrentClientId() { return currentClientId; }
    public void setCurrentClientId(String currentClientId) { this.currentClientId = currentClientId; }

    public Set<Character> getGuessedLetters() { return new HashSet<>(guessedLetters); }
    public void setGuessedLetters(Set<Character> guessedLetters) { this.guessedLetters = new HashSet<>(guessedLetters); }

    public boolean isMultiplayerGame() { return isMultiplayerGame; }
    public void setMultiplayerGame(boolean multiplayerGame) { isMultiplayerGame = multiplayerGame; }

    public int getRoomId() { return roomId; } // Added getter for roomId
    public void setRoomId(int roomId) { this.roomId = roomId; } // Added setter for roomId
}
