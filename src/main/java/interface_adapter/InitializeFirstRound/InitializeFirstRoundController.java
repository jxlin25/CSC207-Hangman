package interface_adapter.InitializeFirstRound;

import use_case.InitializeFirstRound.InitializeFirstRoundInputBoundary;

public class InitializeFirstRoundController {

    private final InitializeFirstRoundInputBoundary initializeFirstRoundInputBoundary;

    public InitializeFirstRoundController(InitializeFirstRoundInputBoundary initializeFirstRoundInputBoundary) {
        this.initializeFirstRoundInputBoundary = initializeFirstRoundInputBoundary;
    }

    /**
     * Execute the Initialize First Round use case.
     */
    public void execute() {
        this.initializeFirstRoundInputBoundary.execute();

    }
}
