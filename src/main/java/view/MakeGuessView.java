package view;

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

    private final HangmanImagePanel hangmanImagePanel = new HangmanImagePanel();
    private final JLabel wordPuzzleLabel = new JLabel("????");
    private final JLabel attemptsLabel = new JLabel("Attempts left: 6");
    private final JLabel roundNumberLabel = new JLabel("Round number: 1");
    private final JButton restartButton;

    // new
    private final JLabel messageLabel = new JLabel(" ");
    private int lastRoundNumber = 1;

    private JPanel alphabetButtonsPanel;

    public MakeGuessView(MakeGuessViewModel viewModel) {

        this.alphabetButtonsPanel = this.createNewLetterButtonsPanel();

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
                    "Are you want to restart the game?",
                    "Confirm Restart",
                    JOptionPane.YES_NO_OPTION
            );
            if (option == JOptionPane.YES_OPTION) {
                returnToStartView();
            }
        });

        // Add everything to the panel
        this.add(hangmanImagePanel);
        this.add(attemptsLabel);
        this.add(roundNumberLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(Box.createVerticalStrut(20));
        this.add(messageLabel);
        this.add(restartButton);
        this.add(wordPuzzleLabel);
        this.add(alphabetButtonsPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Currently unused; all actions handled via button listeners directly.
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        MakeGuessState state = (MakeGuessState) evt.getNewValue();

        System.out.println(state.getGuessedLetter());
        System.out.println(state.getRoundStatus());
        System.out.println(state.getRemainingAttempts());
        System.out.println(state.isGuessCorrect());
        System.out.println(state.isGameOver());
        System.out.println("----------------------------");

        // Use maxAttempts from state (difficulty), fall back to 6 if not set
        int maxAttempts = state.getMaxAttempts() > 0 ? state.getMaxAttempts() : 6;

        if (!state.isGameOver()) {
            int remainingAttempts = state.getRemainingAttempts();

            // Detect if we just started a new round or a new game
            int currentRound = state.getCurrentRoundNumber();

            // New game: round goes back to 1, so reset lastRoundNumber and clear message
            if (currentRound == 1 && lastRoundNumber != 1) {
                lastRoundNumber = 1;
                messageLabel.setText(" ");
            }
            // New round within the same game: round number increases
            else if (currentRound != lastRoundNumber) {
                messageLabel.setText(" ");
                lastRoundNumber = currentRound;
            }

            // Round just ended (win or loss)
            if (state.getRoundStatus().equals(WON) || state.getRoundStatus().equals(LOST)) {
                System.out.println("triggered");

                // Show round end dialog WITH the word
                if (state.getRoundStatus().equals(WON)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "You won this round! The word was: " + state.getCorrectWord(),
                            "Round Result",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "You lost this round. The correct word was: " + state.getCorrectWord(),
                            "Round Result",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }

                // Show the presenter's message (which can still include the word)
                if (state.getMessage() != null && !state.getMessage().isEmpty()) {
                    messageLabel.setText(state.getMessage());
                }

                // DO NOT reset remainingAttempts here; interactor/DAO manage it

                // Renew the letter buttons for the next round
                this.remove(this.alphabetButtonsPanel);
                this.alphabetButtonsPanel = this.createNewLetterButtonsPanel();
                this.add(alphabetButtonsPanel);
                this.revalidate();
                this.repaint();
            } else {
                // Still guessing in current round (normal letter click)
                if (state.getMessage() != null && !state.getMessage().isEmpty()) {
                    messageLabel.setText(state.getMessage());
                }
            }

            this.roundNumberLabel.setText("Round number: " + currentRound);
            hangmanImagePanel.setIncorrectGuesses(maxAttempts - remainingAttempts);
            this.attemptsLabel.setText("Attempts left: " + remainingAttempts);

            // Enable/disable alphabet buttons based on remaining attempts
            if (remainingAttempts <= 0) {
                setAlphabetButtonsEnabled(false);
            } else {
                setAlphabetButtonsEnabled(true);
            }

            // Update the displayed word
            this.wordPuzzleLabel.setText(state.getMaskedWord());
        }
        else {
            // GAME OVER (no more rounds)
            if (state.getMessage() != null && !state.getMessage().isEmpty()) {
                messageLabel.setText(state.getMessage());
            } else if (state.getCorrectWord() != null && !state.getCorrectWord().isEmpty()) {
                messageLabel.setText("Game Over. The correct word was: " + state.getCorrectWord());
            } else {
                messageLabel.setText("Game Over!");
            }

            // Disable alphabet buttons when game is over
            setAlphabetButtonsEnabled(false);

            JOptionPane.showMessageDialog(
                    this,
                    "Game Over!",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private JPanel createNewLetterButtonsPanel() {
        JPanel lettersPanel = new JPanel(new GridLayout(2, 13, 5, 5));

        for (int i = 0; i < 26; i++) {
            final char letter = (char) ('A' + i);
            JButton button = new JButton(String.valueOf(letter));

            button.addActionListener(e -> {

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

    private void returnToStartView() {
        // Clear UI state for a fresh game
        messageLabel.setText(" ");
        lastRoundNumber = 1;
        wordPuzzleLabel.setText("????");

        // Use a neutral placeholder; new game/difficulty will set proper values
        attemptsLabel.setText("Attempts left: 0");
        roundNumberLabel.setText("Round number: 1");
        hangmanImagePanel.setIncorrectGuesses(0);

        // Disable alphabet buttons until a new game actually starts
        setAlphabetButtonsEnabled(false);

        if (viewManagerModel != null) {
            viewManagerModel.setState("Generate Word");
            viewManagerModel.firePropertyChange();
        } else {
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