package entity.round_states;

import constant.StatusConstant;
import entity.Guess;
import entity.Round;
import entity.RoundState;

public class WaitingRoundState implements RoundState {

    @Override
    public void handleGuess(Round round, Guess guess) {
        // First guess starts the round
        round.startRound();
        // delegate to new state
        round.getRoundState().handleGuess(round, guess);
    }

    @Override
    public boolean isOver() {
        return false;
    }

    @Override
    public String getStatus() {
        return StatusConstant.WAITING;
    }
}
