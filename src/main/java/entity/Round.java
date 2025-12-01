package entity;

import constant.StatusConstant;

import java.util.ArrayList;

/**
 * Represents a single round of Hangman game, storing the target word,
 * its status quo, and array of all the guesses been made in the round and the attempts left.
 */
public class Round {

    private WordPuzzle wordPuzzle;
    private String status;
    private ArrayList<Guess> guesses;
    private int attempt;

    public Round(WordPuzzle wordPuzzle) {
        this.wordPuzzle = wordPuzzle;
        this.status = constant.StatusConstant.WAITING;
        this.guesses = new ArrayList<Guess>();
        this.attempt = 6;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Sets the status of the current round to WON.
     */
    public void setWon() {
        this.setStatus(StatusConstant.WON);
    }

    /**
     * Sets the status of the current round to LOST.
     */
    public void setLost() {
        this.setStatus(StatusConstant.LOST);
    }

    /**
     * Sets the status of the current round to GUESSING,
     * marking this round as started.
     */
    public void startRound() {
        this.setStatus(constant.StatusConstant.GUESSING);
    }

    /**
     * Add a guess to this round.
     * @param guess the guess object that is going to be added
     */
    public void addGuess(Guess guess) {
        this.guesses.add(guess);
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public boolean isPuzzleComplete() {
        return wordPuzzle.isPuzzleComplete();
    }

    public WordPuzzle getWordPuzzle() {
        return wordPuzzle;
    }

    /**
     * Checks if a guess is correct to the word puzzle.
     * @param guess A guess that is going to be assessed
     * @return boolean of whether the guess is correct
     */
    public boolean isGuessCorrect(Guess guess) {

        return this.wordPuzzle.isLetterHidden(guess.getLetter());
    }
}

