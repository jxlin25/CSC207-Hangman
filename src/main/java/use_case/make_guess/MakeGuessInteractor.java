package use_case.make_guess;

import entity.Guess;
import entity.Round;

/**
 * The Interactor for the MakeGuess use case.
 */
public class MakeGuessInteractor implements MakeGuessInputBoundary {

    private final MakeGuessOutputBoundary presenter;
    private final MakeGuessDataAccessInterface dataAccessObject;

    public MakeGuessInteractor(MakeGuessOutputBoundary presenter,
                               MakeGuessDataAccessInterface dataAccessObject) {
        this.presenter = presenter;
        this.dataAccessObject = dataAccessObject;
    }

    @Override
    public void execute(MakeGuessInputData inputData) {

        final Guess guess = inputData.getGuess();

        final Round currentRound = dataAccessObject.getCurrentRound();
        final int currentRoundNumber = dataAccessObject.getCurrentRoundNumber();
        final boolean isGuessCorrect = currentRound.isGuessCorrect(guess);

        // Let the Round apply the guess
        //    - reveal letter when correct
        //    - decrease attempts when wrong
        //    - transfer to WON/LOST state if the round is over
        currentRound.handleGuess(guess);

        // Store the updated Round
        dataAccessObject.saveCurrentRound(currentRound);

        final String fullWord = dataAccessObject.getCurrentWord();
        final String currentMaskedWord = dataAccessObject.getCurrentMaskedWord();
        final int currentRemainingAttempts = dataAccessObject.getCurrentRoundAttempt();
        final String roundStatus = dataAccessObject.getCurrentRound().getStatus();

        final boolean roundEnded = dataAccessObject.getCurrentRound().isOver();
        final boolean isLastRound = dataAccessObject.isCurrentRoundTheLastRound();
        final boolean isGameOver = roundEnded && isLastRound;

        // Pack up data into outputData and pass to the presenter
        final MakeGuessOutputData outputData = new MakeGuessOutputData(
                guess,
                isGuessCorrect,
                roundStatus,
                isGameOver,
                currentRemainingAttempts,
                currentRoundNumber,
                currentMaskedWord,
                fullWord
        );
        presenter.updateView(outputData);

        // If the current round ends while not being the last round,
        // move to the next round
        if (roundEnded && !isLastRound) {
            dataAccessObject.startNextRound();
        }

    }
}
