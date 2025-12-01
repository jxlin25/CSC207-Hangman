package entity.round_states;

import constant.StatusConstant;
import entity.Guess;
import entity.Round;
import entity.RoundState;

public class GuessingRoundState implements RoundState {

    @Override
    public void handleGuess(Round round, Guess guess) {

        // record the guess
        round.addGuess(guess);

        final boolean correct = round.isGuessCorrect(guess);

        // If the guess is correct
        if (correct) {

            // Reveal the letter in the word
            round.revealLetter(guess);

            // if the puzzle is complete, move to WON state
            if (round.isPuzzleComplete()) {
                round.setStatus(StatusConstant.WON);
                round.setState(new WonRoundState());
            }
        }
        // If the guess is incorrect
        else {
            round.decreaseAttempt();

            // If there is no attempts left, move to LOST state
            if (round.getAttempt() <= 0) {
                round.setStatus(StatusConstant.LOST);
                round.setState(new LostRoundState());
            }
        }
        // If not complete and there are still attempts remains,
        // we stay in GuessingRoundState
    }

    @Override
    public boolean isOver() {
        return false;
    }

    @Override
    public String getStatus() {
        return StatusConstant.GUESSING;
    }
}
