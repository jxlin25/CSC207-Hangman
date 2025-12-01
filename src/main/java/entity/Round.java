package entity;

import constant.Constants;
import constant.StatusConstant;
import entity.round_states.GuessingRoundState;
import entity.round_states.WaitingRoundState;

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
    private RoundState roundState;

    public Round(WordPuzzle wordPuzzle) {
        this.wordPuzzle = wordPuzzle;
        this.status = constant.StatusConstant.WAITING;
        this.roundState = new WaitingRoundState();
        this.guesses = new ArrayList<Guess>();
        this.attempt = Constants.DEFAULT_MAX_ATTEMPTS;
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
        this.roundState = new GuessingRoundState();
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

    /**
     * Delegate guess-handling to the current state.
     * @param guess Guess object
     */
    public void handleGuess(Guess guess) {
        this.roundState.handleGuess(this, guess);
    }

    /**
     * Reveals a letter in the word puzzle
     * @param guess Guess object the contains the letter
     */
    public void revealLetter(Guess guess) {
        // You will need a method like this in WordPuzzle
        this.wordPuzzle.revealLetter(guess.getLetter());
    }

    public RoundState getRoundState() {
        return roundState;
    }

    public void setState(RoundState roundState) {
        this.roundState = roundState;
    }

    /**
     * Decreases the attempt by 1.
     */
    public void decreaseAttempt() {
        this.attempt--;
    }

    public boolean isOver() {
        return this.roundState.isOver();
    }
}

