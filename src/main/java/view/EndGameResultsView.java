package view;

import interface_adapter.EndGameResults.EndGameResultsState;
import interface_adapter.EndGameResults.EndGameResultsViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EndGameResultsView extends JPanel implements PropertyChangeListener {

    public final String viewName;
    private final EndGameResultsViewModel endGameResultsViewModel;
    private ViewManagerModel viewManagerModel;

    private final JLabel titleLabel;
    private final JLabel statusLabel;
    private final JTextArea roundDetailsArea;  // Changed from JLabel to JTextArea
    private final JLabel attemptsLabel;
    private final JButton restartButton;

    public EndGameResultsView(EndGameResultsViewModel viewModel) {
        this.endGameResultsViewModel = viewModel;
        this.viewName = viewModel.VIEW_NAME;

        this.endGameResultsViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title
        titleLabel = new JLabel("Game Over: Results");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Status Label
        statusLabel = new JLabel("Status: Awaiting Data...");
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Round Details (multi-line text area)
        roundDetailsArea = new JTextArea(5, 30);
        roundDetailsArea.setEditable(false);
        roundDetailsArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        roundDetailsArea.setBackground(this.getBackground());
        roundDetailsArea.setLineWrap(false);
        roundDetailsArea.setText("Awaiting Data...");

        JScrollPane scrollPane = new JScrollPane(roundDetailsArea);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Attempts Label
        attemptsLabel = new JLabel("Total Attempts Used: Awaiting Data...");
        attemptsLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        attemptsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Restart button
        restartButton = new JButton("Play Again");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        restartButton.addActionListener(e -> {
            if (viewManagerModel != null) {
                viewManagerModel.setState("Generate Word");
                viewManagerModel.firePropertyChange();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Cannot restart: ViewManagerModel not set.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Assemble the View
        this.add(Box.createRigidArea(new Dimension(0, 40)));
        this.add(titleLabel);
        this.add(Box.createRigidArea(new Dimension(0, 30)));
        this.add(statusLabel);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(scrollPane);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(attemptsLabel);
        this.add(Box.createRigidArea(new Dimension(0, 30)));
        this.add(restartButton);

        updateView(viewModel.getState());
    }

    public void setViewManagerModel(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        EndGameResultsState state = endGameResultsViewModel.getState();
        updateView(state);
    }

    private void updateView(EndGameResultsState state) {
        String status = state.getFinalStatus();
        String roundDetails = state.getFinalWord();
        int attempts = state.getAttemptsTaken();

        statusLabel.setText(status.isEmpty() ? "Status: N/A" : status);
        roundDetailsArea.setText(roundDetails.isEmpty() ? "No round data" : roundDetails);
        attemptsLabel.setText("Total Attempts Used: " + attempts);

        // Color code the status
        if (status.contains("Victory")) {
            statusLabel.setForeground(new Color(0, 150, 0));
        } else if (status.contains("Defeat")) {
            statusLabel.setForeground(new Color(200, 0, 0));
        } else {
            statusLabel.setForeground(Color.BLACK);
        }
    }

    public String getViewName() {
        return viewName;
    }
}