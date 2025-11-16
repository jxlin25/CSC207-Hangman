package interface_adapter.Hint;

import use_case.Hint.HintOutputBoundary;
import use_case.Hint.HintOutputData;

public class HintPresenter implements HintOutputBoundary {

    private final HintViewModel hintViewModel;

    public HintPresenter(HintViewModel hintViewModel) {
        this.hintViewModel = hintViewModel;
    }

    @Override
    public void prepareSuccessView(HintOutputData outputData) {
    }
}
