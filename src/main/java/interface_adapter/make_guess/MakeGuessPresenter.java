package interface_adapter.make_guess;

import use_case.make_guess.MakeGuessOutputBoundary;
import use_case.make_guess.MakeGuessOutputData;

/** The Presenter for the MakeGuess use case.
 */
public class MakeGuessPresenter implements MakeGuessOutputBoundary {

    private final MakeGuessViewModel makeGuessViewModel;

    public MakeGuessPresenter(MakeGuessViewModel makeGuessViewModel) {
        this.makeGuessViewModel = makeGuessViewModel;
    }

    @Override
    public void updateView(MakeGuessOutputData outputData) {

        final MakeGuessState state = makeGuessViewModel.getState();

        state.setGuessedLetter(String.valueOf(outputData.getGuess().getLetter()));
        state.setGuessCorrect(outputData.isGuessCorrect());
        state.setRoundStatus(outputData.getRoundStatus());
        state.setGameOver(outputData.isGameOver());
        state.setRemainingAttempts(outputData.getRemainingAttempts());
        state.setCurrentRoundNumber(outputData.getCurrentRoundNumber());
        state.setMaskedWord(outputData.getMaskedWord());
        state.setResetAlphabetButtons(false);
        state.setFullWord(outputData.getFullWord());
        makeGuessViewModel.setState(state);
        makeGuessViewModel.firePropertyChanged();
    }
}