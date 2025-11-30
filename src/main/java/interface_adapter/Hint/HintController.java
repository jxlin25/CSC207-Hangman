package interface_adapter.Hint;

import use_case.Hint.HintInputBoundary;

public class HintController {
    private final HintInputBoundary hintInputBoundary;
    public HintController(HintInputBoundary hintInputBoundary) {
        this.hintInputBoundary = hintInputBoundary;
    }

    public void execute() {
        hintInputBoundary.execute();
    }
}
