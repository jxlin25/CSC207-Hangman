package view;

import interface_adapter.EndGameResults.EndGameResultsController;
import interface_adapter.Hint.HintController;
import interface_adapter.InitializeRound.InitializeRoundController;
import interface_adapter.MakeGuess.MakeGuessViewModel;
import interface_adapter.MakeGuess.MakeGuessState;
import interface_adapter.MakeGuess.MakeGuessController;
import interface_adapter.ViewManagerModel;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.Component;

import static Constant.StatusConstant.*;

public class MakeGuessView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "Make Guess";
    private final MakeGuessViewModel makeGuessViewModel;
    private MakeGuessController makeGuessController;
    private ViewManagerModel viewManagerModel;
    private InitializeRoundController initializeRoundController;
    private EndGameResultsController endGameResultsController;
    private HintController hintController;

    private final HangmanImagePanel hangmanImagePanel = new HangmanImagePanel();
    private final JLabel wordPuzzleLabel = new JLabel("????");
    private final JLabel attemptsLabel = new JLabel("Attempts left: 6");
    private final JLabel roundNumberLabel = new JLabel("Round number: 1");
    private final JButton restartButton;
    private final JButton popupRestartButton;
    private final JButton popupShowResultButton;

    private final JButton nextRoundButton;
    private JDialog endGameDialog = new JDialog();
    private final JButton hintButton = new JButton("Hint");
    //private final JLabel hintLabel = new JLabel("Hint: ");
    private final JTextArea hintTextArea = new JTextArea();

    // new
    private final JLabel messageLabel = new JLabel(" ");
    private int lastRoundNumber = 1;

    private JPanel alphabetButtonsPanel;

    public MakeGuessView(MakeGuessViewModel viewModel) {

        this.alphabetButtonsPanel = this.createNewAlphabetButtonsPanel();
        this.makeGuessViewModel = viewModel;
        this.makeGuessViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        wordPuzzleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // new
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        restartButton = new JButton("Restart");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Do you want to restart the game?",
                    "Confirm Restart",
                    JOptionPane.YES_NO_OPTION
            );
            if (option == JOptionPane.YES_OPTION) {
                this.endGameDialog.setVisible(false);
                returnToStartView();
            }
        });

        this.popupRestartButton = new JButton("Restart");
        this.popupRestartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.popupRestartButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Do you want to restart the game?",
                    "Confirm Restart",
                    JOptionPane.YES_NO_OPTION
            );
            if (option == JOptionPane.YES_OPTION) {
                this.endGameDialog.setVisible(false);
                returnToStartView();
            }
        });

        this.popupShowResultButton = new JButton("Show Result");
        this.popupShowResultButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.popupShowResultButton.addActionListener(e -> {
            if (endGameResultsController != null) {
                endGameResultsController.execute();
            }
            this.endGameDialog.setVisible(false);

        });
        // Hint Panel Setup
//        JPanel hintPanel = new JPanel();
//        hintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        hintPanel.setLayout(new BoxLayout(hintPanel, BoxLayout.Y_AXIS));
//        // The word 'apple' is placeholder here; the controller will handle the actual word retrieval logic.
//
//        hintPanel.add(hintButton);
//        hintPanel.add(hintLabel);
        hintButton.addActionListener(event ->
                hintController.execute());

        hintTextArea.setEditable(false);
        hintTextArea.setLineWrap(true);
        hintTextArea.setWrapStyleWord(true);
        hintTextArea.setOpaque(false);
        hintTextArea.setFocusable(false);
        hintTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        hintTextArea.setPreferredSize(new Dimension(400, 200));
        hintTextArea.setMaximumSize(new Dimension(400,200));

        this.nextRoundButton = new JButton("Next Round");
        this.nextRoundButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.nextRoundButton.setEnabled(false);
        this.nextRoundButton.addActionListener(e -> {
            System.out.println("Moving to next round");

            // Reset the letter buttons panel FIRST
            this.resetAlphabetButtonsPanel();

            // Then initialize the next round
            this.initializeRoundController.execute();

            // Disable the next round button until the round is complete again
            this.nextRoundButton.setEnabled(false);
        });

        this.endGameDialog.setVisible(false);

        // Add everything to the panel
        //this.add(hintPanel);
        this.add(hintButton);
        this.add(hintTextArea);
        this.add(hangmanImagePanel);
        this.add(attemptsLabel);
        this.add(roundNumberLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(Box.createVerticalStrut(20));
        this.add(messageLabel);
        this.add(restartButton);
        this.add(nextRoundButton);
        this.add(Box.createVerticalStrut(20));
        this.add(wordPuzzleLabel);
        this.add(alphabetButtonsPanel);

    }

    public void setInitializeRoundController(InitializeRoundController controller) {
        this.initializeRoundController = controller;
    }

    public void setHintController(HintController hintController) {
        this.hintController = hintController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Not used, but required by the interface
    }

    public void setEndGameResultsController(EndGameResultsController controller) {
        this.endGameResultsController = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final MakeGuessState state = (MakeGuessState) evt.getNewValue();

        System.out.println("Guessed letter: " + state.getGuessedLetter());
        System.out.println("Status: " + state.getRoundStatus());
        System.out.println("Remaining attempts: " + state.getRemainingAttempts());
        System.out.println("isGuessCorrect: " + state.isGuessCorrect());
        System.out.println("isGameOver: " + state.isGameOver());
        System.out.println("----------------------------");

        final int maxAttempts = 6;
        final int remainingAttempts = state.getRemainingAttempts();

        this.roundNumberLabel.setText("Round number: " + state.getCurrentRoundNumber());
        hangmanImagePanel.setIncorrectGuesses(maxAttempts - remainingAttempts);
        this.attemptsLabel.setText("Attempts left: " + remainingAttempts);
        this.wordPuzzleLabel.setText(state.getMaskedWord());

        if (state.getResetAlphabetButtons()) {
            this.resetAlphabetButtonsPanel();
        }

        // If the current round is ended
        if (state.getRoundStatus().equals(WON) || state.getRoundStatus().equals(LOST)) {
            System.out.println("Round over");
            this.disableAlphabetButtons();
            this.nextRoundButton.setEnabled(true);

            // If the current ended round is the last round, the game also ends
            if (state.isGameOver()) {
//                JOptionPane.showMessageDialog(
//                        this,
//                        "Game Over!",
//                        "Game Over",
//                        JOptionPane.INFORMATION_MESSAGE
//                );
                final JOptionPane endGamePane = new JOptionPane(
                        "Game Over! Do you want to restart?",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.DEFAULT_OPTION,
                        null,
                        new Object[]{this.popupRestartButton, this.popupShowResultButton}
                );

                this.endGameDialog = endGamePane.createDialog(this, "Game Over!");

                // Close the program when the endGameDialog is closed
                endGameDialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });

                endGameDialog.setVisible(true);
            }
        }

        // Update hint display
        if (state.getHintText() != null) {
            System.out.println("Hint updated");
            hintTextArea.setText("Hint: " + state.getHintText());
        }
        else {
            hintTextArea.setText("Hint: ");
        }
    }

    private void disableAlphabetButtons() {
        for (Component component : this.alphabetButtonsPanel.getComponents()) {
            if (component instanceof JButton) {
                component.setEnabled(false);
            }
        }
    }

    private JPanel createNewAlphabetButtonsPanel() {
        final JPanel alphabetButtonsPanel = new JPanel(new GridLayout(2, 13, 5, 5));
        fillAlphabetButtonsPanel(alphabetButtonsPanel);
        return alphabetButtonsPanel;
    }

    private void fillAlphabetButtonsPanel(JPanel lettersPanel) {
        for (int i = 0; i < 26; i++) {
            final char letter = (char) ('A' + i);
            JButton button = new JButton(String.valueOf(letter));

            button.addActionListener(e -> {
                makeGuessController.execute(Character.toLowerCase(letter));
                button.setEnabled(false);
                button.setBackground(Color.LIGHT_GRAY);
            });

            lettersPanel.add(button);
        }
    }

    private void resetAlphabetButtonsPanel() {
        // Removes all the old buttons
        alphabetButtonsPanel.removeAll();
        // Fills the panel with new buttons
        fillAlphabetButtonsPanel(alphabetButtonsPanel);
        alphabetButtonsPanel.revalidate();
        alphabetButtonsPanel.repaint();
    }

    private void returnToStartView() {
        this.resetAlphabetButtonsPanel();
        if (viewManagerModel != null) {
            viewManagerModel.setState("Generate Word");
            viewManagerModel.firePropertyChange();
        }
        else {
            JOptionPane.showMessageDialog(
                    this,
                    "Navigation error: viewManagerModel not set.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void setAlphabetButtonsEnabled(boolean enabled) {
        if (alphabetButtonsPanel == null) return;

        for (Component comp : alphabetButtonsPanel.getComponents()) {
            if (comp instanceof JButton) {
                comp.setEnabled(enabled);
            }
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewManagerModel(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    public void setMakeGuessController(MakeGuessController controller) {
        this.makeGuessController = controller;
    }
}