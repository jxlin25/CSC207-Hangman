package use_case.initialize_round;


public class InitializeRoundInteractor implements InitializeRoundInputBoundary {

    private final InitializeRoundOutputBoundary presenter;
    private final InitializeRoundDataAccessInterface dataAccessObject;

    public InitializeRoundInteractor(InitializeRoundOutputBoundary presenter, InitializeRoundDataAccessInterface dataAccessObject) {
        this.presenter = presenter;
        this.dataAccessObject = dataAccessObject;
    }

    @Override
    public void execute() {

        final InitializeRoundOutputData outputData =
                new InitializeRoundOutputData(
                        this.dataAccessObject.getCurrentRoundAttempt(),
                        this.dataAccessObject.getCurrentRoundNumber(),
                        this.dataAccessObject.getCurrentMaskedWord(),
                        this.dataAccessObject.getTotalRoundNumber()
                );

        this.presenter.initializeView(outputData);
    }
}
