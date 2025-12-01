package use_case.initialize_round;

/**
 * The interactor for InitializeRound use case.
 */
public class InitializeRoundInteractor implements InitializeRoundInputBoundary {

    private final InitializeRoundOutputBoundary presenter;
    private final InitializeRoundDataAccessInterface initializeRoundDataAccessObject;

    public InitializeRoundInteractor(InitializeRoundOutputBoundary presenter,
                                     InitializeRoundDataAccessInterface initializeRoundDataAccessObject) {
        this.presenter = presenter;
        this.initializeRoundDataAccessObject = initializeRoundDataAccessObject;
    }

    @Override
    public void execute() {

        final InitializeRoundOutputData outputData =
                new InitializeRoundOutputData(
                        this.initializeRoundDataAccessObject.getCurrentRoundAttempt(),
                        this.initializeRoundDataAccessObject.getCurrentRoundNumber(),
                        this.initializeRoundDataAccessObject.getCurrentMaskedWord(),
                        this.initializeRoundDataAccessObject.getTotalRoundNumber()
                );

        this.presenter.initializeView(outputData);
    }
}
