package view;

import interface_adapter.GenerateWord.GenerateWordController;
import interface_adapter.GenerateWord.GenerateWordViewModel;
import interface_adapter.InitializeFirstRound.InitializeFirstRoundController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GenerateWordView extends JPanel implements PropertyChangeListener {
    private final String viewName = "Generate Word";

    private GenerateWordController generateWordController;
    private final GenerateWordViewModel generateWordViewModel;
    private InitializeFirstRoundController initializeFirstRoundController;

    private final JButton startGameButton;
    private final JComboBox<Integer> numberSelector;

    // Store last selected attempts (default difficulty)
    private int selectedAttempts = 6;

    public GenerateWordView(GenerateWordViewModel viewModel) {
        this.generateWordViewModel = viewModel;
        this.generateWordViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JLabel title = new JLabel("Let's Start Game!");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        numberSelector = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6});
        numberSelector.setAlignmentX(Component.CENTER_ALIGNMENT);
        numberSelector.setSelectedItem(1);

        JLabel numberLabel = new JLabel("Number of Words:");
        numberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        startGameButton = new JButton("START");
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Modified to ask difficulty, then call controller with attempts
        startGameButton.addActionListener(evt -> {
            if (evt.getSource().equals(startGameButton)) {
                Integer attempts = chooseDifficultyWithDialog();
                if (attempts == null) {
                    // User closed dialog, cancel starting game
                    return;
                }
                int numberOfWords = (Integer) numberSelector.getSelectedItem();
                generateWordController.execute(numberOfWords, selectedAttempts);// Generate the HangmanGame entity for the game
                initializeFirstRoundController.execute();// Update the data of the first word to the view
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel numberPanel = new JPanel();
        numberPanel.add(numberLabel);
        numberPanel.add(numberSelector);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startGameButton);

        this.add(title);
        this.add(numberPanel);
        this.add(buttonPanel);
    }

    /**
     * Shows a modal JDialog for difficulty selection
     * Returns: 8 (Easy), 6 (Default), 4 (Hard), or null if closed
     */
    private Integer chooseDifficultyWithDialog() {
        JDialog dialog = new JDialog(
                SwingUtilities.getWindowAncestor(this),
                "Select Difficulty",
                Dialog.ModalityType.APPLICATION_MODAL
        );
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel("Please choose a difficulty level:");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);

        JButton easyButton = new JButton("Easy (8 attempts)");
        JButton defaultButton = new JButton("Default (6 attempts)");
        JButton hardButton = new JButton("Hard (4 attempts)");

        panel.add(easyButton);
        panel.add(defaultButton);
        panel.add(hardButton);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        final Integer[] chosenAttempts = new Integer[1];

        easyButton.addActionListener(e -> {
            chosenAttempts[0] = 8;
            dialog.dispose();
        });
        defaultButton.addActionListener(e -> {
            chosenAttempts[0] = 6;
            dialog.dispose();
        });
        hardButton.addActionListener(e -> {
            chosenAttempts[0] = 4;
            dialog.dispose();
        });

        dialog.setVisible(true); // blocks until dialog closes
        return chosenAttempts[0];
    }

    public String getViewName() {
        return viewName;
    }

    public void setGenerateWordController(GenerateWordController generateWordController) {
        this.generateWordController = generateWordController;
    }

    public void setInitializeFirstRoundController(InitializeFirstRoundController initializeFirstRoundController) {
        this.initializeFirstRoundController = initializeFirstRoundController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final var state = generateWordViewModel.getState();

        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this,
                    state.getError(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
