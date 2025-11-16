package interface_adapter.Hint;

import use_case.hint.HintOutputBoundary;
import use_case.hint.HintOutputData;

public class HintPresenter implements HintOutputBoundary {

    private final HintViewModel hintViewModel;

    public HintPresenter(HintViewModel hintViewModel) {
        this.hintViewModel = hintViewModel;
    }

    @Override
    public void prepareSuccessView(HintOutputData outputData) {
        hintViewModel.setHintText(outputData.getHint().getText());
        hintViewModel.firePropertyChanged();
    }
}
