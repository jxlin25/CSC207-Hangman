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
     * @param words A list of words, where each round contains a word
     */
    public HangmanGame(List<String> words) {
        this.rounds = new ArrayList<>();

        //creating rounds of the game
        for (String word : words) {
            WordPuzzle wordPuzzle = new WordPuzzle(word.toCharArray());
            this.rounds.add(new Round(wordPuzzle));
        }
        this.currentRoundIndex = 0;
    }


    /**
     * Gets the currently active round.
     * @return The current Round object.
     */
    public Round getCurrentRound() {
        return rounds.get(currentRoundIndex);
    }

    public Round getRound(int index){
        if(index >= 0 && index < this.rounds.size()){
            return this.rounds.get(index);
        }
        else{
            return null;
        }

    }

    /**
     * Attempts to move to the next round.
     * @return true if successfully moved to a new round, false if all rounds are over.
     */
    public boolean startNextRound(boolean won) {
        if (won){
            this.getCurrentRound().setWON();
        }
        else{
            this.getCurrentRound().setLOST();
        }
        if (currentRoundIndex >= rounds.size() - 1) {
            return false; // cannot move to next round
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
        for  (Round round : rounds) {
            if (!(round.getStatus() == WON || round.getStatus() == LOST)) {
                return false;
            }
        }
        return true;
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

    public Round getRound(int index){
        if(index >= 0 && index < this.rounds.size()){
            return this.rounds.get(index);
        }
        else{
            return null;
        }

    }
}
