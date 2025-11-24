package view;

import Constant.Constants;
import interface_adapter.InitializeRound.InitializeRoundController;
import interface_adapter.MakeGuess.MakeGuessViewModel;
import interface_adapter.MakeGuess.MakeGuessState;
import interface_adapter.MakeGuess.MakeGuessController;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.Component;

import static Constant.StatusConstant.*;

public class MakeGuessView extends JPanel implements ActionListener, PropertyChangeListener {

    private final MakeGuessViewModel makeGuessViewModel;
    private MakeGuessController makeGuessController;
    private InitializeRoundController initializeRoundController;
    private final HangmanImagePanel hangmanImagePanel = new HangmanImagePanel();
    private final JLabel wordPuzzleLabel = new JLabel("????");
    private final JLabel attemptsLabel = new JLabel("Attempts left: 6");
    private final JLabel roundNumberLabel = new JLabel("Round number: 1");
    private JPanel alphabetButtonsPanel;

    public MakeGuessView(MakeGuessViewModel viewModel) {

        this.alphabetButtonsPanel = this.createNewLetterButtonsPanel();

        this.makeGuessViewModel = viewModel;
        this.makeGuessViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        wordPuzzleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add everything to the panel
        this.add(hangmanImagePanel);
        this.add(attemptsLabel);
        this.add(roundNumberLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(wordPuzzleLabel);
        this.add(alphabetButtonsPanel);
    }

    public void setMakeGuessController(MakeGuessController controller) {
        this.makeGuessController = controller;
    }

    public void setInitializeRoundController(InitializeRoundController controller) {
        this.initializeRoundController = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

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

        // The maxAttempts can be changed by difficulty level setting
        final int maxAttempts = 6;
        final int remainingAttempts = state.getRemainingAttempts();

        this.roundNumberLabel.setText("Round number: " + state.getCurrentRoundNumber());
        hangmanImagePanel.setIncorrectGuesses(maxAttempts - remainingAttempts);
        this.attemptsLabel.setText("Attempts left: " + remainingAttempts);
        // Update the displayed word
        this.wordPuzzleLabel.setText(state.getMaskedWord());

        // If the current round is ended
        if (state.getRoundStatus().equals(WON) || state.getRoundStatus().equals(LOST)) {
            System.out.println("Round over");

            // If the current ended round is the last round, the game also ends
            if (state.isGameOver()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Game Over!",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
            else {
                // TODO: put these code into the action listener of 'Next Round' JButton
                System.out.println("Moving to next round");
                this.initializeRoundController.execute();
                this.resetLetterButtonsPanel();

            }
        }

//        if (!state.isGameOver()) {
//            // The maxAttempts can be changed by difficulty level setting
//            final int maxAttempts = 6;
//            int remainingAttempts = state.getRemainingAttempts();
//
//            this.roundNumberLabel.setText("Round number: " + state.getCurrentRoundNumber());
//            hangmanImagePanel.setIncorrectGuesses(maxAttempts - remainingAttempts);
//            this.attemptsLabel.setText("Attempts left: " + remainingAttempts);
//
//            if (state.getRoundStatus().equals(WON) || state.getRoundStatus().equals(LOST)) {
//                System.out.println("Round over");
//
//                remainingAttempts = maxAttempts;
//                // renew the buttons
//                this.remove(this.alphabetButtonsPanel);
//                this.alphabetButtonsPanel = this.createNewLetterButtonsPanel();
//                this.add(alphabetButtonsPanel);
//                this.revalidate();
//                this.repaint();
//            }
//
//            // Update the displayed word
//            this.wordPuzzleLabel.setText(state.getMaskedWord());
//        }
//        else {
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Game Over!",
//                    "Game Over",
//                    JOptionPane.INFORMATION_MESSAGE
//            );
//        }

    }

    private JPanel createNewLetterButtonsPanel() {
        final JPanel lettersPanel = new JPanel(new GridLayout(2, 13, 5, 5));

        for (int i = 0; i < Constants.NUMBER_OF_ENGLISH_ALPHABET; i++) {
            final char letter = (char) ('A' + i);
            final JButton button = new JButton(String.valueOf(letter));

            button.addActionListener(actionEvent -> {
                // Make a guess by the letter
                makeGuessController.execute(Character.toLowerCase(letter));
                // Disable the button so it can't be clicked again
                button.setEnabled(false);
                // Change the button color
                button.setBackground(Color.LIGHT_GRAY);
            });

            lettersPanel.add(button);
        }

        return lettersPanel;
    }

    private void resetLetterButtonsPanel() {
        this.remove(this.alphabetButtonsPanel);
        this.alphabetButtonsPanel = this.createNewLetterButtonsPanel();
        this.add(alphabetButtonsPanel);
        this.revalidate();
        this.repaint();
    }

    /**
     * Gets the name of this view.
     * @return "Make Guess"
     */
    public String getViewName() {
        return "Make Guess";
    }

}