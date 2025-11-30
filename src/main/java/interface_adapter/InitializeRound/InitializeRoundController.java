package interface_adapter.InitializeRound;

import use_case.InitializeRound.InitializeRoundInputBoundary;

public class InitializeRoundController {

    private final InitializeRoundInputBoundary initializeRoundInputBoundary;

    public InitializeRoundController(InitializeRoundInputBoundary initializeRoundInputBoundary) {
        this.initializeRoundInputBoundary = initializeRoundInputBoundary;
    }

    /**
     * Execute the Initialize First Round use case.
     */
    public void execute() {
        this.initializeRoundInputBoundary.execute();
    }
}
