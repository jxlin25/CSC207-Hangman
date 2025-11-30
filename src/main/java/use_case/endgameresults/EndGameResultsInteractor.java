package use_case.endgameresults;

import java.util.ArrayList;
import java.util.List;

import data_access.InMemoryHangmanDataAccessObject;
import entity.HangmanGame;
import entity.Round;
import interface_adapter.EndGameResults.EndGameResultsState.RoundResult;



/**
 * The Interactor for the EndGameResults use case.
 */
public class EndGameResultsInteractor implements EndGameResultsInputBoundary {

    private final EndGameResultsOutputBoundary endGameResultsPresenter;
    private final InMemoryHangmanDataAccessObject hangmanGameDAO;

    public EndGameResultsInteractor(EndGameResultsOutputBoundary endGameResultsPresenter,
                                    InMemoryHangmanDataAccessObject hangmanGameDAO) {
        this.endGameResultsPresenter = endGameResultsPresenter;
        this.hangmanGameDAO = hangmanGameDAO;
    }

    @Override
    public void execute(EndGameResultsInputData inputData) {
        final HangmanGame game = hangmanGameDAO.getHangmanGame();

        // Determine overall game status based on the last round
        final String finalStatus = "Game Over";

        // Build round-by-round results
        final List<RoundResult> roundResults = new ArrayList<>();

        for (int i = 0; i < game.getRounds().size(); i++) {
            final Round round = game.getRounds().get(i);

            System.out.println(STR."Round \{i + 1}:");
            System.out.println(STR."  Status: \{round.getStatus()}");
            System.out.println(STR."  Remaining Attempts: \{round.getAttempt()}");
            System.out.println(STR."  Attempts Used: \{6 - round.getAttempt()}");

            // Calculate attempts used for this round (6 - remaining)
            final int attemptsUsed = 6 - round.getAttempt();

            // Get word
            final String word = new String(round.getWordPuzzle().getLetters());

            // Get status
            final String status = round.getStatus();
            final String statusText;
            if (status.equals(Constant.StatusConstant.WON)) {
                statusText = "Won";
            }
            else {
                statusText = "Lost";
            }

            // Create round result
            final RoundResult result = new RoundResult(
                    i + 1,
                    word,
                    attemptsUsed,
                    statusText
            );

            roundResults.add(result);
        }

        endGameResultsPresenter.present(finalStatus, roundResults);
    }
}