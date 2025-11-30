package use_case.game_session;

import entity.Player;
import entity.game_session.GameState;
import entity.game_session.GamePhase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;

public class GameInteractor {

    public GameState processWordSubmission(GameState currentState, String wordSetterId, String submittedWord) {
        // Validate phase
        if (currentState.getCurrentPhase() != GamePhase.WORD_SELECTION) {
            throw new IllegalStateException("Not in word selection phase");
        }

        // Validate player is the word setter
        if (!wordSetterId.equals(currentState.getCurrentWordSetterId())) {
            throw new IllegalArgumentException("Player is not the word setter");
        }

        // Validate word using GameState's method
        if (!currentState.isWordValid(submittedWord)) {
            throw new IllegalArgumentException("Invalid word");
        }

        // Initialize game for guessing phase using GameState's method
        currentState.initializeForGuessing(submittedWord);
        return currentState;
    }

    public GameState processGuess(GameState currentState, String guesserId, char letter) {
        // Validate phase
        if (currentState.getCurrentPhase() != GamePhase.GUESSING) {
            throw new IllegalStateException("Not in guessing phase");
        }

        // Validate player is the guesser
        if (!guesserId.equals(currentState.getCurrentGuesserId())) {
            throw new IllegalArgumentException("Player is not the guesser");
        }

        // Convert letter to uppercase
        letter = Character.toUpperCase(letter);

        // Check if the letter has already been guessed
        if (currentState.getGuessedLetters().contains(letter)) {
            return currentState; // No change
        }

        // Add the letter to guessed letters
        currentState.getGuessedLetters().add(letter);

        String secretWord = currentState.getSecretWord();
        
        // Check if the letter is in the secret word
        if (secretWord.indexOf(letter) >= 0) {
            // Correct guess - update revealed word
            updateRevealedWord(currentState, letter);
        } else {
            // Incorrect guess
            currentState.setIncorrectGuessesCount(currentState.getIncorrectGuessesCount() + 1); // This now correctly increments because the setter sets the new total.
        }

        // Check win/lose conditions
        checkGameConditions(currentState);

        return currentState;
    }

    private void updateRevealedWord(GameState state, char letter) {
        char[] revealed = state.getRevealedWord().toCharArray();
        String secret = state.getSecretWord();

        for (int i = 0; i < secret.length(); i++) {
            if (secret.charAt(i) == letter) {
                revealed[i] = letter;
            }
        }

        state.setRevealedWord(new String(revealed));
    }

    private void checkGameConditions(GameState state) {
        // Check win
        if (!state.getRevealedWord().contains("_")) {
            state.setCurrentPhase(GamePhase.ROUND_END);
            state.setRoundWinnerId(state.getCurrentGuesserId());
            // Update scores: increment guesser's score
            state.getScores().put(state.getCurrentGuesserId(), state.getScores().getOrDefault(state.getCurrentGuesserId(), 0) + 1);
        }
        // Check lose
        else if (state.getIncorrectGuessesCount() >= state.getMaxIncorrectGuesses()) {
            state.setCurrentPhase(GamePhase.ROUND_END);
            // The word setter wins
            state.setRoundWinnerId(state.getCurrentWordSetterId());
            // Update scores: increment word setter's score
            state.getScores().put(state.getCurrentWordSetterId(), state.getScores().getOrDefault(state.getCurrentWordSetterId(), 0) + 1);
        }
    }
}