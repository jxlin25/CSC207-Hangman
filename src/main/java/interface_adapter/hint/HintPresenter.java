package interface_adapter.hint;

import use_case.hint.HintOutputBoundary;
import use_case.hint.HintOutputData;

public class HintPresenter implements HintOutputBoundary {

    private final HintViewModel vm;

    public HintPresenter(HintViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void present(HintOutputData outputData) {
        vm.setHintText(outputData.getHint().getText());
        vm.firePropertyChanged();
    }
}
