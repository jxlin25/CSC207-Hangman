package interface_adapter.initialize_round;

import use_case.initialize_round.InitializeRoundInputBoundary;

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
