package interface_adapter.MakeGuess;

import use_case.MakeGuess.MakeGuessOutputBoundary;
import use_case.MakeGuess.MakeGuessOutputData;

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
        state.setPuzzleComplete(outputData.isPuzzleComplete());


        if (outputData.isGuessCorrect()) {
            state.setMessage("Correct guess!");
        } else {
            state.setMessage("Wrong guess, try again.");
        }


        makeGuessViewModel.setState(state);

    }
}
