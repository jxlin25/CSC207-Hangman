package interface_adapter.Hint;

import use_case.Hint.HintInputBoundary;

/**
 * Controller for the Generate Word Use Case.
 */
public class HintController {
    private final HintInputBoundary hintInputBoundary;

    public HintController(HintInputBoundary hintInputBoundary) {
        this.hintInputBoundary = hintInputBoundary;
    }

    /**
     * Executes the Generate Word Use Case.
     */
    public void execute() {
        hintInputBoundary.execute();
    }
}
