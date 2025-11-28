package interface_adapter.Hint;

import use_case.Hint.HintInputBoundary;
import use_case.Hint.HintInputData;

public class HintController {
    private final HintInputBoundary hintInputBoundary;
    public HintController(HintInputBoundary hintInputBoundary) {
        this.hintInputBoundary = hintInputBoundary;
    }

    public void execute(String word) {
        HintInputData hintInputData = new HintInputData(word);
        hintInputBoundary.execute(hintInputData);
    }
}
