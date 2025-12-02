package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import entity.GameStats;
import interface_adapter.stats.StatsController;
import interface_adapter.stats.StatsState;
import interface_adapter.stats.StatsViewModel;

/**
 * Simple statistics view showing only wins and losses.
 */
public class StatsView extends JDialog implements PropertyChangeListener {
    private static final int BORDER_GAP = 10;
    private static final String FONT_NAME = "Arial";
    private static final int TITLE_FONT_SIZE = 16;
    private static final int STATS_PANEL_ROWS = 3;
    private static final int STATS_PANEL_COLUMNS = 2;
    private static final int STATS_PANEL_BORDER_PADDING = 20;
    private static final int STATS_FONT_SIZE = 14;
    private static final int WIDTH = 300;
    private static final int HEIGHT = 200;

    private StatsController statsController;
    private StatsViewModel statsViewModel;
    private JLabel roundsWonLabel;
    private JLabel roundsLostLabel;
    private JLabel winRateLabel;

    public StatsView(JFrame parent, StatsController statsController, StatsViewModel statsViewModel) {
        super(parent, "Game Statistics", true);
        this.statsController = statsController;
        this.statsViewModel = statsViewModel;

        initializeUi();
        setupListeners();

        // Load stats when dialog opens
        statsController.execute();

        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    /**
     * Showing stats.
     */
    public void showStats() {
        setVisible(true);
    }

    /**
     * Initializing UI.
     */
    private void initializeUi() {
        setLayout(new BorderLayout(BORDER_GAP, BORDER_GAP));

        // Title
        final JLabel titleLabel = new JLabel("Game Statistics", SwingConstants.CENTER);
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, TITLE_FONT_SIZE));
        add(titleLabel, BorderLayout.NORTH);

        // Stats display
        final JPanel statsPanel = createStatsPanel();
        add(statsPanel, BorderLayout.CENTER);

        // Close button
        final JPanel buttonPanel = new JPanel();
        final JButton closeButton = new JButton("Close");
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        closeButton.addActionListener(error -> dispose());

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    private JPanel createStatsPanel() {
        final JPanel panel = new JPanel(new GridLayout(STATS_PANEL_ROWS, STATS_PANEL_COLUMNS, BORDER_GAP, BORDER_GAP));
        panel.setBorder(BorderFactory.createEmptyBorder(STATS_PANEL_BORDER_PADDING, STATS_PANEL_BORDER_PADDING,
                STATS_PANEL_BORDER_PADDING, STATS_PANEL_BORDER_PADDING));

        panel.add(new JLabel("Rounds Won:"));
        this.roundsWonLabel = new JLabel("0");
        roundsWonLabel.setFont(new Font(FONT_NAME, Font.BOLD, STATS_FONT_SIZE));
        panel.add(roundsWonLabel);

        panel.add(new JLabel("Rounds Lost:"));
        this.roundsLostLabel = new JLabel("0");
        roundsLostLabel.setFont(new Font(FONT_NAME, Font.BOLD, STATS_FONT_SIZE));
        panel.add(roundsLostLabel);

        panel.add(new JLabel("Round Win Rate:"));
        this.winRateLabel = new JLabel("0%");
        winRateLabel.setFont(new Font(FONT_NAME, Font.BOLD, STATS_FONT_SIZE));
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
        final StatsState state = statsViewModel.getState();
        if (state.getStats() != null) {
            updateStatsDisplay(state.getStats());
        }
    }
}