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

    public MakeGuessPresenter(MakeGuessViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @Override
    public void updateView(MakeGuessOutputData data) {
        viewModel.setMaskedWord(data.getMaskedWord());
        viewModel.setRemainingAttempts(data.getRemainingAttempts());
        viewModel.setGuessedLetters(data.getGuessedLetters());
        viewModel.setRoundStatus(data.getRoundStatus());
        viewModel.setCorrectWordOnLoss(data.getCorrectWordOnLoss());
    }
}
