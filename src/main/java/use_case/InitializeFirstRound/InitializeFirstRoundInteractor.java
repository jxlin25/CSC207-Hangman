package use_case.InitializeFirstRound;

public class InitializeFirstRoundInteractor implements InitializeFirstRoundInputBoundary {

    private InitializeFirstRoundOutputBoundary presenter;
    private InitializeFirstRoundDataAccessInterface hangmanGameDAO;

    public InitializeFirstRoundInteractor(InitializeFirstRoundOutputBoundary presenter, InitializeFirstRoundDataAccessInterface hangmanGameDAO) {
        this.presenter = presenter;
        this.hangmanGameDAO = hangmanGameDAO;
    }

    public void execute() {

        InitializeFirstRoundOutputData outputData = new InitializeFirstRoundOutputData(this.hangmanGameDAO.getFirstRoundAttempt(),  this.hangmanGameDAO.getFirstRoundNumber(), this.hangmanGameDAO.getFirstMaskedWord());



        this.presenter.initializeView(outputData);
    }
}
