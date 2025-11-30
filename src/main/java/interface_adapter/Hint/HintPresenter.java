package interface_adapter.Hint;

import interface_adapter.MakeGuess.MakeGuessState;
import interface_adapter.MakeGuess.MakeGuessViewModel;
import use_case.Hint.HintOutputBoundary;
import use_case.Hint.HintOutputData;

public class HintPresenter implements HintOutputBoundary {
    private final MakeGuessViewModel makeGuessViewModel;

    public HintPresenter(MakeGuessViewModel makeGuessViewModel) {
        this.makeGuessViewModel = makeGuessViewModel;
    }

    @Override
    public void prepareSuccessView(HintOutputData hintOutputData) {
        MakeGuessState state = makeGuessViewModel.getState();
        state.setHintText(hintOutputData.getHint());

        makeGuessViewModel.setState(state);
        makeGuessViewModel.firePropertyChanged();
    }
}
