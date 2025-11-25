
package use_case.EndGameResults;

import data_access.InMemoryHangmanDataAccessObject;
import entity.Round;

public class EndGameResultsInteractor implements EndGameResultsInputBoundary {

    final EndGameResultsOutputBoundary EndGameResultsPresenter;
    final InMemoryHangmanDataAccessObject hangmanGameDAO;

    public EndGameResultsInteractor(EndGameResultsOutputBoundary EndGameResultsPresenter, InMemoryHangmanDataAccessObject hangmanGameDAO) {
        this.EndGameResultsPresenter = EndGameResultsPresenter;
        this.hangmanGameDAO = hangmanGameDAO;
    }

    @Override
    public void execute(EndGameResultsInputData inputData) {
        Round currentRound = hangmanGameDAO.getCurrentRound();

        String finalStatus = currentRound.getStatus(); // WON or LOST

        int attemptsTaken = 6 - currentRound.getAttempt(); // need to change it so it aligns with difficulty

        String finalWord = currentRound.getWordPuzzle().getMaskedWord();

        EndGameResultsPresenter.present(finalStatus, finalWord, attemptsTaken);
    }


}