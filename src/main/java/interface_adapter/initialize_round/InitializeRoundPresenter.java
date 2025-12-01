package interface_adapter.initialize_round;

import constant.StatusConstant;
import interface_adapter.make_guess.MakeGuessState;
import interface_adapter.make_guess.MakeGuessViewModel;
import use_case.initialize_round.InitializeRoundOutputBoundary;
import use_case.initialize_round.InitializeRoundOutputData;

public class InitializeRoundPresenter implements InitializeRoundOutputBoundary {

    private final MakeGuessViewModel makeGuessViewModel;

    public InitializeRoundPresenter(MakeGuessViewModel makeGuessViewModel) {

        this.makeGuessViewModel = makeGuessViewModel;
    }

    @Override
    public void initializeView(InitializeRoundOutputData outputData) {

        final MakeGuessState state = makeGuessViewModel.getState();

        state.setGuessedLetter(null);
        state.setGuessCorrect(false);
        state.setRoundStatus(StatusConstant.GUESSING);
        state.setGameOver(false);
        state.setRemainingAttempts(outputData.getRemainingAttempts());
        state.setCurrentRoundNumber(outputData.getCurrentRoundNumber());
        state.setMaskedWord(outputData.getMaskedWord());
        state.setHintText(null);
        state.setResetAlphabetButtons(true);
        state.setUsedhint(false);

        makeGuessViewModel.setState(state);
        makeGuessViewModel.firePropertyChanged();
    }


}
