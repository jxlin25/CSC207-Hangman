package interface_adapter.MakeGuess;

import stats.InMemorySessionStatisticsRepository;
import stats.SessionStatistics;
import use_case.MakeGuess.MakeGuessOutputBoundary;
import use_case.MakeGuess.MakeGuessOutputData;
import view.GameOverDialog;
import viewModel.MakeGuessViewModel;

import javax.swing.*;

public class MakeGuessPresenter implements MakeGuessOutputBoundary {
    private final MakeGuessViewModel viewModel;
    private final InMemorySessionStatisticsRepository statsRepo;
    private final Runnable onPlayAgain; // supplied by AppBuilder to start a new round

    public MakeGuessPresenter(MakeGuessViewModel viewModel,
                              InMemorySessionStatisticsRepository statsRepo,
                              Runnable onPlayAgain) {
        this.viewModel = viewModel;
        this.statsRepo = statsRepo;
        this.onPlayAgain = onPlayAgain;
    }

    @Override
    public void present(MakeGuessOutputData outputData) {
        // Update session stats if round concluded
        SessionStatistics stats = statsRepo.getStats();
        if ("WON".equals(outputData.getRoundStatus())) {
            stats.recordRoundWin(0, 0); // if you track attempts/time per round, pass here
        } else if ("LOST".equals(outputData.getRoundStatus())) {
            stats.recordRoundLoss(0, 0);
        }

        // Update the view model
        viewModel.setMaskedWord(outputData.getMaskedWord());
        viewModel.setRemainingAttempts(outputData.getRemainingAttempts());
        viewModel.setGuessedLetters(outputData.getGuessedLetters());
        viewModel.setRoundStatus(outputData.getRoundStatus());
        viewModel.firePropertyChanged();

        // If lost, show the correct word and offer replay
        if ("LOST".equals(outputData.getRoundStatus())) {
            SwingUtilities.invokeLater(() -> {
                JFrame owner = viewModel.getOwnerFrame();
                GameOverDialog dialog = new GameOverDialog(
                        owner,
                        "You lost! The word was: " + outputData.getCorrectWordOnLoss(),
                        () -> {
                            dialog.dispose();
                            onPlayAgain.run();
                        },
                        () -> {
                            dialog.dispose();
                            owner.dispose();
                        }
                );
                dialog.setVisible(true);
            });
        }
    }
}
