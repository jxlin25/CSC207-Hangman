package view;

import Constant.Constants;
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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.Component;

import static Constant.StatusConstant.*;

public class MakeGuessView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "Make Guess";
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
    private final JButton nextRoundButton;
    private final JButton hintButton = new JButton("Hint");
    private final JLabel hintLabel = new JLabel("Hint: ");

    private JPanel alphabetButtonsPanel;

    public MakeGuessView(MakeGuessViewModel viewModel) {

        this.alphabetButtonsPanel = this.createNewLetterButtonsPanel();
        this.makeGuessViewModel = viewModel;
        this.makeGuessViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        wordPuzzleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        restartButton = new JButton("Restart");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Do you want to restart the game?",
                    "Confirm Restart",
                    JOptionPane.YES_NO_OPTION
            );
            this.resetLetterButtonsPanel();
            if (option == JOptionPane.YES_OPTION) {
                returnToStartView();
            }
        });

        // Hint Panel Setup
        JPanel hintPanel = new JPanel();
        hintPanel.setLayout(new BoxLayout(hintPanel, BoxLayout.Y_AXIS));
        // The word 'apple' is placeholder here; the controller will handle the actual word retrieval logic.
        hintButton.addActionListener(e -> hintController.execute("apple"));
        hintPanel.add(hintButton);
        hintPanel.add(hintLabel);


        this.nextRoundButton = new JButton("Next Round");
        this.nextRoundButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.nextRoundButton.setEnabled(false);
        this.nextRoundButton.addActionListener(e -> {
            System.out.println("Moving to next round");

            // Reset the letter buttons panel FIRST
            this.resetLetterButtonsPanel();

            // Then initialize the next round
            this.initializeRoundController.execute();

            // Disable the next round button until the round is complete again
            this.nextRoundButton.setEnabled(false);
        });

        // Add everything to the panel
        this.add(hangmanImagePanel);
        this.add(attemptsLabel);
        this.add(roundNumberLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(restartButton);
        this.add(nextRoundButton);
        this.add(Box.createVerticalStrut(20));
        this.add(wordPuzzleLabel);
        this.add(alphabetButtonsPanel);
        this.add(hintPanel);
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
        MakeGuessState state = (MakeGuessState) evt.getNewValue();

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

        // If the current round is ended
        if (state.getRoundStatus().equals(WON) || state.getRoundStatus().equals(LOST)) {
            System.out.println("Round over");
            this.disableLetterButtons();
            this.nextRoundButton.setEnabled(true);

            // If the current ended round is the last round, the game also ends
            if (state.isGameOver()) {
                if (endGameResultsController != null) {
                    endGameResultsController.execute();
                }
            }
        }

        // Update hint display
        if (state.getHintText() != null) {
            hintLabel.setText("Hint: " + state.getHintText());
        } else {
            hintLabel.setText("Hint: ");
        }
    }

    private void disableLetterButtons() {
        for (Component component : this.alphabetButtonsPanel.getComponents()) {
            if (component instanceof JButton) {
                component.setEnabled(false);
            }
        }
    }

    private JPanel createNewLetterButtonsPanel() {
        JPanel lettersPanel = new JPanel(new GridLayout(2, 13, 5, 5));

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

        return lettersPanel;
    }

    private void resetLetterButtonsPanel() {
        this.remove(this.alphabetButtonsPanel);
        this.alphabetButtonsPanel = this.createNewLetterButtonsPanel();
        // Re-add the alphabet buttons panel right before the hint panel
        // Find the index of the hint panel to ensure correct insertion order
        Component[] components = this.getComponents();
        int hintPanelIndex = -1;
        for (int i = 0; i < components.length; i++) {
            // We use the last component which should be the hint panel based on the constructor
            if (components[i] instanceof JPanel && components[i] != this.alphabetButtonsPanel) {
                hintPanelIndex = i;
            }
        }

        // Insert the new alphabet panel before the hint panel or just at the end if the index isn't found
        if (hintPanelIndex != -1) {
            this.add(alphabetButtonsPanel, hintPanelIndex);
        } else {
            this.add(alphabetButtonsPanel);
        }

        this.revalidate();
        this.repaint();
    }

    private void returnToStartView() {
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