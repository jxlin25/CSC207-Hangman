package entity;

import Constant.StatusConstant;

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

    public Round(WordPuzzle wordPuzzle, int attempts) {
        this.wordPuzzle = wordPuzzle;
        this.status = Constant.StatusConstant.WAITING;
        this.guesses = new ArrayList<Guess>();
        this.attempt = attempts; //can be modified once all the difficulty levels are implemented
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setWon() {
        this.setStatus(Constant.StatusConstant.WON);
    }

    public void setLost() {
        this.setStatus(StatusConstant.LOST);
    }

    public void startRound() {
        this.setStatus(Constant.StatusConstant.GUESSING);
    }

    public void addGuess(Guess guess) {
        this.guesses.add(guess);
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public boolean isPuzzleComplete(){
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

