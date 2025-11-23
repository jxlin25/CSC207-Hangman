package entity;

import java.util.ArrayList;

public class Round {
    //leave for multiplayer
    //private String roundId;
    private WordPuzzle wordPuzzle;
    private String status;
    private ArrayList<Guess> guesses;
    private int attempt;

    public Round(WordPuzzle wordPuzzle) {
        this.wordPuzzle = wordPuzzle;
        this.status = constant.StatusConstant.WAITING;
        this.guesses = new ArrayList<Guess>();
        this.attempt = 6; //can be modified once all the difficulty levels are implemented
    }

    public boolean isGuessCorrect(Guess guess) {
        return this.wordPuzzle.isLetterHidden(guess.getLetter());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setWON() {
        this.setStatus(constant.StatusConstant.WON);
    }

    public void setLOST() {
        this.setStatus(constant.StatusConstant.LOST);
    }

    public void startRound() {
        this.setStatus(constant.StatusConstant.GUESSING);
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
}

