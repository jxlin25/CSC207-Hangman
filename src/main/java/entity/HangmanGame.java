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
    private int maxAttempts;
    private int hintAttempts;

    /**
     * Creates a new game.
     * @param words A list of words, where each round contains a word
     */
    public HangmanGame(List<String> words) {
        this.rounds = new ArrayList<>();

        // Creating rounds of the game
        for (String word : words) {
            final WordPuzzle wordPuzzle = new WordPuzzle(word.toCharArray());
            this.rounds.add(new Round(wordPuzzle));

        }
        this.currentRoundIndex = 0;
    }

    public ArrayList<Round> getRounds() {
        return this.rounds;
    }

    public int getCurrentRoundIndex() {
        return currentRoundIndex;
    }

    /**
     * Gets the currently active round.
     * @return The current Round object.
     */
    public Round getCurrentRound() {
        return rounds.get(currentRoundIndex);
    }

    /**
     * Gets a Round object by index.
     * @param index index of the Round object
     * @return the corresponding Round object
     */
    public Round getRound(int index) {
        Round result = null;
        if (index >= 0 && index < this.rounds.size()) {
            result = this.rounds.get(index);
        }
        return result;
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
     * @return number of won round
     */
    public int getRoundsWon() {
        int wins = 0;

        for (int i = 0; i < currentRoundIndex; i++) {
            if (rounds.get(i).getStatus().equals(constant.StatusConstant.WON)) {
                wins++;
            }
        }

        return wins;
    }

    /**
     * Attempts to move to the next round.
     * @param win whether this round is won or lost
     * @return true if successfully moved to a new round, false if all rounds are over.
     */
    public boolean startNextRound(boolean win) {
        if (win) {
            this.getCurrentRound().setWon();
        }
        else {
            this.getCurrentRound().setLost();
        }
        if (currentRoundIndex >= rounds.size() - 1) {
            return false;
        }

        // Otherwise move to next
        currentRoundIndex++;
        this.getCurrentRound().startRound();
        return true;
    }

    /**
     * Checks if all rounds are over.
     * @return boolean of whether all the rounds are over
     */
    public boolean isGameOver() {

        // Check if all the round has the status of either WON or LOST
        // If not, immediately return false
        for (Round round : rounds) {
            if (!(round.getStatus() == constant.StatusConstant.WON || round.getStatus() == constant.StatusConstant.LOST)) {
                return false;
            }
        }
        return true;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public int getHintAttempts() {
        return hintAttempts;
    }

    public void setHintAttempts(int hintAttempts) {
        this.hintAttempts = hintAttempts;
    }

    /**
     * Decreases the hint attempts.
     */
    public void decreaseHintAttempt() {
        this.hintAttempts = hintAttempts - 1;
    }

}
