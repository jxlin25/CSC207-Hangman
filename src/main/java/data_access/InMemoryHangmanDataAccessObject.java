package data_access;

import entity.Guess;
import entity.HangmanGame;
import entity.Round;
import use_case.MakeGuess.MakeGuessHangmanGameDataAccessInterface;

public class InMemoryHangmanDataAccessObject implements MakeGuessHangmanGameDataAccessInterface {

    private HangmanGame currentHangmanGame;

    public InMemoryHangmanDataAccessObject(HangmanGame initialHangmanGame) {
        this.currentHangmanGame = initialHangmanGame;
    }

    @Override
    public HangmanGame getHangmanGame() {
        return this.currentHangmanGame;
    }

    @Override
    public Round getCurrentRound() {
        return this.getHangmanGame().getCurrentRound();
    }

    @Override
    public void decreaseCurrentRoundAttempt() {
        this.getCurrentRound().setAttempt(this.getCurrentRound().getAttempt() - 1);
    }

    @Override
    public void addGuessToCurrentRound(Guess guess){
        this.getCurrentRound().addGuess(guess);
    }

    // Set current round as WON and move on to the next round
    @Override
    public boolean setCurrentRoundWonAndStartNextRound() {
        this.getCurrentRound().setWon();
        return this.getHangmanGame().startNextRound();
    }

    // Set current round as LOST and move on to the next round
    @Override
    public boolean setCurrentRoundLostAndStartNextRound() {
        this.getCurrentRound().setLOST();
        return this.getHangmanGame().startNextRound();
    }

    @Override
    public int getCurrentRoundAttempt() {
        return  this.getCurrentRound().getAttempt();
    }
}
