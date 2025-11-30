package use_case.initialize_round;

public class InitializeRoundInteractor implements InitializeRoundInputBoundary {

    private InitializeRoundOutputBoundary presenter;
    private InitializeRoundDataAccessInterface hangmanGameDAO;

    public InitializeRoundInteractor(InitializeRoundOutputBoundary presenter, InitializeRoundDataAccessInterface hangmanGameDAO) {
        this.presenter = presenter;
        this.hangmanGameDAO = hangmanGameDAO;
    }

    @Override
    public void execute() {

        InitializeRoundOutputData outputData =
                new InitializeRoundOutputData(
                        this.hangmanGameDAO.getCurrentRoundAttempt(),
                        this.hangmanGameDAO.getCurrentRoundNumber(),
                        this.hangmanGameDAO.getCurrentMaskedWord(),
                        this.hangmanGameDAO.getTotalRoundNumber()
                );

        this.presenter.initializeView(outputData);
    }
}
