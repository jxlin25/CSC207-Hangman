package view;

import interface_adapter.Stats.StatsController;
import interface_adapter.Stats.StatsViewModel;
import interface_adapter.Stats.StatsState;
import entity.GameStats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Simple statistics view showing only wins and losses
 */
public class StatsView extends JDialog implements PropertyChangeListener {
    private StatsController statsController;
    private StatsViewModel statsViewModel;
    private JLabel winsLabel;
    private JLabel lossesLabel;
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

        panel.add(new JLabel("Wins:"));
        this.winsLabel = new JLabel("0");
        winsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(winsLabel);

        panel.add(new JLabel("Losses:"));
        this.lossesLabel= new JLabel("0");
        lossesLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lossesLabel);

        panel.add(new JLabel("Win Rate:"));
        this.winRateLabel = new JLabel("0%");
        winRateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(winRateLabel);

        return panel;
    }

    private void setupListeners() {
        statsViewModel.addPropertyChangeListener(this);
    }

    private void updateStatsDisplay(GameStats stats) {
        this.winsLabel.setText(String.valueOf(stats.getWins()));
        this.lossesLabel.setText(String.valueOf(stats.getLosses()));
        this.winRateLabel.setText(String.format("%.1f%%", stats.getWinRate()));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        StatsState state = statsViewModel.getState();
        if (state.getStats() != null) {
            updateStatsDisplay(state.getStats());
        }
    }
}