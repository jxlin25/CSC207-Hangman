package view;

import interface_adapter.EndGameResults.EndGameResultsState;
import interface_adapter.EndGameResults.EndGameResultsViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EndGameResultsView extends JPanel implements PropertyChangeListener {

    // The name used by the ViewManagerModel to identify this screen
    public final String viewName;

    private final EndGameResultsViewModel endGameResultsViewModel;

    // UI Components for displaying the results
    private final JLabel titleLabel;
    private final JLabel statusLabel;
    private final JLabel wordLabel;
    private final JLabel attemptsLabel;

    /**
     * Constructor for the EndGameResultsView.
     * @param viewModel The ViewModel associated with this view.
     */
    public EndGameResultsView(EndGameResultsViewModel viewModel) {
        this.endGameResultsViewModel = viewModel;
        this.viewName = viewModel.VIEW_NAME;

        this.endGameResultsViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // --- Title ---
        titleLabel = new JLabel("Game Over: Results");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Result Labels ---
        statusLabel = new JLabel("Status: Awaiting Data...");
        wordLabel = new JLabel("Word: Awaiting Data...");
        attemptsLabel = new JLabel("Attempts: Awaiting Data...");

        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        wordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        attemptsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Assemble the View ---
        this.add(titleLabel);
        this.add(Box.createRigidArea(new Dimension(0, 30)));
        this.add(statusLabel);
        this.add(wordLabel);
        this.add(attemptsLabel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        // Initialize the view with the current state data
        updateView(viewModel.getState());
    }

    /**
     * This method is called by the EndGameResultsViewModel
     * when the results state has been updated by the Presenter.
     *
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        EndGameResultsState state = endGameResultsViewModel.getState();
        updateView(state);
    }

    /**
     * Helper method to update all UI components based on the current state.
     */
    private void updateView(EndGameResultsState state) {
        // Retrieve data from the state
        String status = state.getFinalStatus();
        String word = state.getFinalWord();
        int attempts = state.getAttemptsTaken();

        // Update the GUI labels
        statusLabel.setText("Status: " + (status.isEmpty() ? "N/A" : status));
        wordLabel.setText("The Word Was: " + (word.isEmpty() ? "N/A" : word));
        attemptsLabel.setText("Attempts Taken: " + attempts);

    }

    public String getViewName() {
        return viewName;
    }
}