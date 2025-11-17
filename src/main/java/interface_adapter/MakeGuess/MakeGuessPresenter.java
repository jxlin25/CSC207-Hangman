package interface_adapter.MakeGuess;

import use_case.MakeGuess.MakeGuessOutputBoundary;
import use_case.MakeGuess.MakeGuessOutputData;

import static Constant.StatusConstant.*;

public class MakeGuessPresenter implements MakeGuessOutputBoundary {

    private final MakeGuessViewModel makeGuessViewModel;

    public MakeGuessPresenter(MakeGuessViewModel makeGuessViewModel) {
        this.makeGuessViewModel = makeGuessViewModel;
    }

    @Override
    public void updateView(MakeGuessOutputData outputData) {

        MakeGuessState state = makeGuessViewModel.getState();


        state.setGuessedLetter(String.valueOf(outputData.getGuess().getLetter()));
        state.setGuessCorrect(outputData.isGuessCorrect());
        state.setRoundStatus(outputData.getRoundStatus());


        if (outputData.isGuessCorrect()) {
            state.setMessage("Correct guess!");
        } else {
            state.setMessage("Wrong guess, try again.");
        }

        if (outputData.getRoundStatus().equals(WON)) {
            state.setMessage("You Won this round!");
        }

        if (outputData.getRoundStatus().equals(LOST)) {
            state.setMessage("You Lost this round!");
        }

        if (outputData.isGameOver()) {
            state.setMessage("Game Over!");
        }


        makeGuessViewModel.setState(state);

    }
}
