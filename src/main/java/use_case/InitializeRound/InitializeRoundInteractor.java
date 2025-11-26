package use_case.InitializeRound;

import use_case.MakeGuess.HangmanGameDataAccessInterface;

public class InitializeRoundInteractor implements InitializeRoundInputBoundary {

    private InitializeRoundOutputBoundary presenter;
    private HangmanGameDataAccessInterface hangmanGameDAO;

    public InitializeRoundInteractor(InitializeRoundOutputBoundary presenter, HangmanGameDataAccessInterface hangmanGameDAO) {
        this.presenter = presenter;
        this.hangmanGameDAO = hangmanGameDAO;
    }

    @Override
    public void execute() {

        InitializeRoundOutputData outputData =
                new InitializeRoundOutputData(
                        this.hangmanGameDAO.getCurrentRoundAttempt(),
                        this.hangmanGameDAO.getCurrentRoundNumber(),
                        this.hangmanGameDAO.getCurrentMaskedWord()
                );

        this.presenter.initializeView(outputData);
    }
}
