package entity.round_states;

import constant.StatusConstant;
import entity.Guess;
import entity.Round;
import entity.RoundState;

public class WonRoundState implements RoundState {

    @Override
    public void handleGuess(Round round, Guess guess) {

    }

    @Override
    public boolean isOver() {
        return true;
    }

    @Override
    public String getStatus() {
        return StatusConstant.WON;
    }
}
