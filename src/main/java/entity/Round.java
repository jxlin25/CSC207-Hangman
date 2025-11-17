package entity;

import java.util.ArrayList;

import static Constant.StatusConstant.*;

public class Round {
    //leave for multiplayer
    //private String roundId;
    private WordPuzzle word;
    private String status;
    private ArrayList<Guess> guesses;
    private int attempt;

    public Round(WordPuzzle word) {
        this.word = word;
        this.status = WAITING;
        this.guesses = new ArrayList<Guess>();
        this.attempt = 6; //can be modified once all the difficulty levels are implemented
    }

    public boolean isGuessCorrect(Guess guess) {
        return this.word.isLetterHidden(guess.getLetter());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setWON() {
        this.setStatus(WON);
    }

    public void setLOST() {
        this.setStatus(LOST);
    }

    public void startRound() {
        this.setStatus(GUESSING);
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
        return word.isPuzzleComplete();
    }
}

