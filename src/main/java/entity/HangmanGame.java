package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the overall game state, including a list of all rounds.
 * This class is the main "entity" for the whole game.
 */
public class HangmanGame {

    private ArrayList<Round> rounds;
    private int currentRoundIndex;

    /**
     * Creates a new game.
     * @param words A list of words, where each word becomes a round.
     */
    public HangmanGame(List<String> words) {
        this.rounds = new ArrayList<>();
        for (String word : words) {
            this.rounds.add(new Round(word));
        }
        this.currentRoundIndex = 0;
    }



    /**
     * Gets the currently active round.
     * @return The current Round object, or null if the game is over.
     */
    public Round getCurrentRound() {
        if (isGameOverallOver()) {
            return null; // No more rounds
        }
        return rounds.get(currentRoundIndex);
    }

    /**
     * Attempts to move to the next round.
     * @return true if successfully moved to a new round, false if all rounds are over.
     */
    public boolean startNextRound() {
        if (!isGameOverallOver()) {
            currentRoundIndex++;
            return !isGameOverallOver();
        }
        return false;
    }

    /**
     * Checks if all rounds have been played.
     */
    public boolean isGameOverallOver() {
        return currentRoundIndex >= rounds.size();
    }



    public int getCurrentRoundNumber() {
        // Add 1 because index is 0-based
        return currentRoundIndex + 1;
    }

    public int getTotalRounds() {
        return rounds.size();
    }

    /**
     * Calculates the total number of rounds won so far.
     */
    public int getRoundsWon() {
        int wins = 0;

        for (int i = 0; i < currentRoundIndex; i++) {
            if (rounds.get(i).isWon()) {
                wins++;
            }
        }

        if (getCurrentRound() != null && getCurrentRound().isOver() && getCurrentRound().isWon()){
            wins++;
        }
        return wins;
    }
}
