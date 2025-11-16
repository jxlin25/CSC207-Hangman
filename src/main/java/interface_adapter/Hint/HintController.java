package interface_adapter.Hint;

import use_case.hint.HintInputBoundary;
import use_case.hint.HintInputData;

public class HintController {
    private final HintInputBoundary hintInputBoundary;

    public HintController(HintInputBoundary hintInputBoundary) { this.hintInputBoundary = hintInputBoundary; }

    public void execute(String word) {
        hintInputBoundary.execute(new HintInputData(word));
    }
}
