package view;

import interface_adapter.EndGameResults.EndGameResultsState;
import interface_adapter.EndGameResults.EndGameResultsState.RoundResult;
import interface_adapter.EndGameResults.EndGameResultsViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EndGameResultsView extends JPanel implements PropertyChangeListener {

    public final String viewName;
    private final EndGameResultsViewModel endGameResultsViewModel;
    private ViewManagerModel viewManagerModel;

    private final JLabel titleLabel;
    private final JLabel statusLabel;
    private final JTable resultsTable;
    private final DefaultTableModel tableModel;
    private final JButton restartButton;

    public EndGameResultsView(EndGameResultsViewModel viewModel) {
        this.endGameResultsViewModel = viewModel;
        this.viewName = viewModel.VIEW_NAME;

        this.endGameResultsViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top panel with title and status
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        // Title
        titleLabel = new JLabel("Results");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Status Label
        statusLabel = new JLabel("Status: Awaiting Data...");
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(titleLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        topPanel.add(statusLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Table setup
        String[] columnNames = {"Round #", "Word", "Attempts Used", "Result"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        resultsTable = new JTable(tableModel);
        resultsTable.setFont(new Font("Monospaced", Font.PLAIN, 16));
        resultsTable.setRowHeight(30);
        resultsTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));

        // Center align all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < resultsTable.getColumnCount(); i++) {
            resultsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setPreferredSize(new Dimension(600, 200));

        // Restart button
        restartButton = new JButton("Play Again");
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

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(restartButton);

        // Assemble the View
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
        EndGameResultsState state = endGameResultsViewModel.getState();
        updateView(state);
    }

    private void updateView(EndGameResultsState state) {
        String status = state.getFinalStatus();

        statusLabel.setText(status.isEmpty() ? "Status: N/A" : status);

        // Color code the status
        
        // Clear existing table data
        tableModel.setRowCount(0);

        // Populate table with round results
        for (RoundResult result : state.getRoundResults()) {
            Object[] rowData = {
                    result.getRoundNumber(),
                    result.getWord(),
                    result.getAttemptsUsed(),
                    result.getStatus()
            };
            tableModel.addRow(rowData);
        }
    }

    public String getViewName() {
        return viewName;
    }
}