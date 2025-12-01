package view;

import interface_adapter.stats.StatsController;
import interface_adapter.stats.StatsViewModel;
import interface_adapter.stats.StatsState;
import entity.GameStats;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Simple statistics view showing only wins and losses
 */
public class StatsView extends JDialog implements PropertyChangeListener {
    private StatsController statsController;
    private StatsViewModel statsViewModel;
    private JLabel roundsWonLabel;
    private JLabel roundsLostLabel;
    private JLabel winRateLabel;


    public StatsView(JFrame parent, StatsController statsController, StatsViewModel statsViewModel) {
        super(parent, "Game Statistics", true);
        this.statsController = statsController;
        this.statsViewModel = statsViewModel;

        initializeUI();
        setupListeners();

        // Load stats when dialog opens
        statsController.execute();

        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    public void showStats() {
        setVisible(true);
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel titleLabel = new JLabel("Game Statistics", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        // Stats display
        JPanel statsPanel = createStatsPanel();
        add(statsPanel, BorderLayout.CENTER);

        // Close button
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Close");
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        closeButton.addActionListener(e -> dispose());

        setPreferredSize(new Dimension(300, 200));
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Rounds Won:"));
        this.roundsWonLabel = new JLabel("0");
        roundsWonLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(roundsWonLabel);

        panel.add(new JLabel("Rounds Lost:"));
        this.roundsLostLabel = new JLabel("0");
        roundsLostLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(roundsLostLabel);

        panel.add(new JLabel("Round Win Rate:"));
        this.winRateLabel = new JLabel("0%");
        winRateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(winRateLabel);

        return panel;
    }

    private void setupListeners() {
        statsViewModel.addPropertyChangeListener(this);
    }

    private void updateStatsDisplay(GameStats stats) {
        this.roundsWonLabel.setText(String.valueOf(stats.getRoundsWon()));
        this.roundsLostLabel.setText(String.valueOf(stats.getRoundsLost()));
        this.winRateLabel.setText(String.format("%.1f%%", stats.getRoundWinRate()));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        StatsState state = statsViewModel.getState();
        if (state.getStats() != null) {
            updateStatsDisplay(state.getStats());
        }
    }
}