package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import interface_adapter.endgame_results.EndGameResultsState;
import interface_adapter.endgame_results.EndGameResultsState.RoundResult;
import interface_adapter.endgame_results.EndGameResultsViewModel;
import interface_adapter.ViewManagerModel;

/**
 * The View for displaying end game results with a table of round-by-round performance.
 */
public class EndGameResultsView extends JPanel implements PropertyChangeListener {

    private static final String SANS_SERIF_FONT_NAME = "SansSerif";
    private static final int TITLE_FONT_SIZE = 32;
    private static final int STATUS_FONT_SIZE = 24;
    private static final int TABLE_HEADER_FONT_SIZE = 16;
    private static final int RESTART_BUTTON_FONT_SIZE = 16;

    private static final int SPACING_SIZE = 10;
    private static final int BORDER_SIZE = 20;
    private static final int VERTICAL_GAP = 20;
    private static final int ROW_HEIGHT = 30;
    private static final int TABLE_WIDTH = 600;
    private static final int TABLE_HEIGHT = 200;

    private final String viewName;
    private final EndGameResultsViewModel endGameResultsViewModel;
    private ViewManagerModel viewManagerModel;

    private JLabel statusLabel;
    private DefaultTableModel tableModel;

    public EndGameResultsView(EndGameResultsViewModel viewModel) {
        this.endGameResultsViewModel = viewModel;
        this.viewName = viewModel.getViewName();

        this.endGameResultsViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout(SPACING_SIZE, SPACING_SIZE));
        this.setBorder(BorderFactory.createEmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));

        final JPanel topPanel = createTopPanel();
        final JScrollPane scrollPane = createResultsTable();
        final JPanel buttonPanel = createButtonPanel();

        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        updateView(viewModel.getState());
    }

    public void setViewManagerModel(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final EndGameResultsState state = endGameResultsViewModel.getState();
        updateView(state);
    }

    private void updateView(EndGameResultsState state) {
        final String status = state.getFinalStatus();

        if (status.isEmpty()) {
            statusLabel.setText("Status: N/A");
        }
        else {
            statusLabel.setText(status);
        }

        tableModel.setRowCount(0);

        for (RoundResult result : state.getRoundResults()) {
            final Object[] rowData = {
                    result.getRoundNumber(),
                    result.getWord(),
                    result.getAttemptsUsed(),
                    result.getStatus(),
            };
            tableModel.addRow(rowData);
        }
    }

    public String getViewName() {
        return viewName;
    }

    private JPanel createTopPanel() {
        final JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        // Title
        final JLabel titleLabel = new JLabel("Results");
        titleLabel.setFont(new Font("Serif", Font.BOLD, TITLE_FONT_SIZE));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Status Label
        statusLabel = new JLabel("Status: Awaiting Data...");
        statusLabel.setFont(new Font(SANS_SERIF_FONT_NAME, Font.BOLD, STATUS_FONT_SIZE));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(titleLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, VERTICAL_GAP)));
        topPanel.add(statusLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, VERTICAL_GAP)));

        return topPanel;
    }

    private JScrollPane createResultsTable() {
        // Table setup
        final String[] columnNames = {"Round #", "Word", "Attempts Used", "Result"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        final JTable resultsTable = new JTable(tableModel);
        resultsTable.setFont(new Font("Monospaced", Font.PLAIN, TABLE_HEADER_FONT_SIZE));
        resultsTable.setRowHeight(ROW_HEIGHT);
        resultsTable.getTableHeader().setFont(new Font(SANS_SERIF_FONT_NAME, Font.BOLD, TABLE_HEADER_FONT_SIZE));

        // Center align all columns
        final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < resultsTable.getColumnCount(); i++) {
            resultsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        final JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));

        return scrollPane;
    }

    private JPanel createButtonPanel() {
        // Restart button
        final JButton restartButton = new JButton("Play Again");
        restartButton.setFont(new Font(SANS_SERIF_FONT_NAME, Font.BOLD, RESTART_BUTTON_FONT_SIZE));
        restartButton.addActionListener(event -> handleRestartButton());

        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(restartButton);

        return buttonPanel;
    }

    private void handleRestartButton() {
        if (viewManagerModel != null) {
            viewManagerModel.setState("Generate Word");
            viewManagerModel.firePropertyChange();
        }
        else {
            JOptionPane.showMessageDialog(
                    this,
                    "Cannot restart: ViewManagerModel not set.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}