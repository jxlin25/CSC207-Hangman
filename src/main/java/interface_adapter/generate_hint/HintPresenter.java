package interface_adapter.generate_hint;

import interface_adapter.make_guess.MakeGuessState;
import interface_adapter.make_guess.MakeGuessViewModel;
import use_case.generate_hint.HintOutputBoundary;
import use_case.generate_hint.HintOutputData;

public class HintPresenter implements HintOutputBoundary {
    private final MakeGuessViewModel makeGuessViewModel;

    public HintPresenter(MakeGuessViewModel makeGuessViewModel) {
        this.makeGuessViewModel = makeGuessViewModel;
    }

    @Override
    public void prepareSuccessView(HintOutputData hintOutputData) {
        final MakeGuessState state = makeGuessViewModel.getState();
        state.setHintText(hintOutputData.getHint());
        state.setHintAttempts(hintOutputData.getRemainHintAttempts());

        makeGuessViewModel.setState(state);
        makeGuessViewModel.firePropertyChanged();
    }
}
