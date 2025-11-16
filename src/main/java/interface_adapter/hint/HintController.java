package interface_adapter.hint;

import use_case.hint.HintInputBoundary;
import use_case.hint.HintInputData;

public class HintController {
    private final HintInputBoundary interactor;

    public HintController(HintInputBoundary interactor) { this.interactor = interactor; }

    public void requestHint(String word) {
        interactor.execute(new HintInputData(word));
    }
}
