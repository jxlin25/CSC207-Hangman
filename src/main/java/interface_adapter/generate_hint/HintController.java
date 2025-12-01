package interface_adapter.generate_hint;

import use_case.generate_hint.HintInputBoundary;

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
