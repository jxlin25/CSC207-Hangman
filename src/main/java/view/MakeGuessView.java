package view;

import entity.game_session.GamePhase;
import entity.game_session.GameState;
import interface_adapter.MakeGuess.MakeGuessViewModel;
import interface_adapter.MakeGuess.MakeGuessState;
import interface_adapter.MakeGuess.MakeGuessController;
import interface_adapter.ViewManagerModel; // Assuming ViewManagerModel is accessible to switch views

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static Constant.StatusConstant.*; // Assuming this provides WON, LOST, GUESSING

public class MakeGuessView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "Make Guess";
    private final MakeGuessViewModel makeGuessViewModel;
    private MakeGuessController makeGuessController; // Controller for making guesses
    private final ViewManagerModel viewManagerModel; // Added ViewManagerModel

    // UI Elements for general game display (part of guesserGamePanel)
    private final HangmanImagePanel hangmanImagePanel = new HangmanImagePanel();
    private final JLabel wordPuzzleLabel = new JLabel("????");
    private final JLabel attemptsLabel = new JLabel("Attempts left: 6");
    private final JLabel roundNumberLabel = new JLabel("Round number: 1");
    private JPanel alphabetButtonsPanel; // Existing alphabet buttons

    // Multiplayer specific UI elements
    private JLabel roleLabel; // Shows player's role (Word Setter, Guesser)
    private JPanel dynamicPanel; // Panel to switch between wordSetterPanel and guesserGamePanel content
    private JPanel wordSetterInfoPanel; // Shows info for the word setter
    private JPanel guesserGamePanel; // Contains the main hangman game UI for the guesser

    // Constructor modified to accept ViewManagerModel
    public MakeGuessView(MakeGuessViewModel viewModel, MakeGuessController controller, ViewManagerModel viewManagerModel) {
        this.makeGuessViewModel = viewModel;
        this.makeGuessController = controller;
        this.viewManagerModel = viewManagerModel; // Assign ViewManagerModel
        this.makeGuessViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        initializeMultiplayerUI(); // Setup the dynamic UI for multiplayer
        updateUIBasedOnState(makeGuessViewModel.getState()); // Initial UI update
    }

    private void initializeMultiplayerUI() {
        // Common elements at the top
        roleLabel = new JLabel("Your Role: Loading...");
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(roleLabel);
        this.add(Box.createVerticalStrut(10));

        dynamicPanel = new JPanel(); // This panel will hold either wordSetterInfoPanel or guesserGamePanel
        dynamicPanel.setLayout(new BorderLayout()); // Use BorderLayout for dynamic content
        this.add(dynamicPanel); // Add the dynamic panel to the view

        // Initialize both possible panels
        wordSetterInfoPanel = createWordSetterInfoPanel();
        guesserGamePanel = createGuesserGamePanel();
    }

    private JPanel createWordSetterInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel message = new JLabel("You are the Word Setter. Waiting for the Guesser...");
        message.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel wordSetDisplay = new JLabel("Your word: "); // Will display the word the setter chose
        wordSetDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(message);
        panel.add(wordSetDisplay);
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JPanel createGuesserGamePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Add existing single-player UI elements here
        wordPuzzleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        attemptsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        roundNumberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        hangmanImagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // This panel will be refreshed, so alphabetButtonsPanel is initialized within propertyChange
        // For initial creation, we can make a dummy one or ensure it's handled by updateGuesserGamePanel
        alphabetButtonsPanel = createNewLetterButtonsPanel(true); // Create initially enabled
        alphabetButtonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);


        panel.add(hangmanImagePanel);
        panel.add(attemptsLabel);
        panel.add(roundNumberLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(wordPuzzleLabel);
        panel.add(alphabetButtonsPanel); // Added the alphabet buttons panel here
        return panel;
    }

    // This method will be called by the presenter to update the view
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        MakeGuessState state = (MakeGuessState) evt.getNewValue();
        updateUIBasedOnState(state);
    }

    private void updateUIBasedOnState(MakeGuessState state) {
        // Clear previous content in dynamic panel
        dynamicPanel.removeAll();
        String currentClientId = state.getCurrentClientId(); // Get current client's ID from state

        // Check if it's a multiplayer game
        if (state.isMultiplayerGame()) {
            if (state.getRoundStatus().equals(GamePhase.WORD_SELECTION.toString())) {
                if (currentClientId != null && currentClientId.equals(state.getCurrentWordSetterId())) {
                    showWordSetterInterface(state);
                } else {
                    // Guesser or spectator during word selection
                    showSpectatorInterface(state); // Show a waiting message for non-setter
                }
            } else if (state.getRoundStatus().equals(GamePhase.GUESSING.toString())) {
                if (currentClientId != null && currentClientId.equals(state.getCurrentGuesserId())) {
                    showGuesserInterface(state);
                } else if (currentClientId != null && currentClientId.equals(state.getCurrentWordSetterId())) {
                    showWordSetterInterface(state); // Word setter watches guesser's progress
                } else {
                    showSpectatorInterface(state);
                }
            } else if (state.getRoundStatus().equals(GamePhase.ROUND_END.toString())) {
                showRoundEndInterface(state);
            } else if (state.getRoundStatus().equals(GamePhase.GAME_OVER.toString())) {
                roleLabel.setText("Game Over!");
                JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                // Optionally switch to a "Game Over" view or similar
            }
        } else {
            // Single-player game logic
            roleLabel.setText("Your Role: Player (Single-player)");
            dynamicPanel.add(guesserGamePanel, BorderLayout.CENTER);
            updateGuesserGamePanel(state, true); // Always enabled in single-player
            if (state.isGameOver()){
                JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
        this.revalidate();
        this.repaint();
    }


    private void showWordSetterInterface(MakeGuessState state) {
        roleLabel.setText("Your Role: Word Setter");
        dynamicPanel.add(wordSetterInfoPanel, BorderLayout.CENTER);
        
        // Update word setter panel to show the word they set (if available)
        // Assuming wordSetDisplay is the 2nd component (index 1) in wordSetterInfoPanel,
        // and it's a JLabel.
        Component[] components = wordSetterInfoPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (label.getText().startsWith("Your word:")) { // Find the specific label
                    if (state.getSecretWord() != null && !state.getSecretWord().isEmpty()) {
                        label.setText("Your word: " + state.getSecretWord());
                    } else {
                        label.setText("Your word: (Not set yet)");
                    }
                    break;
                }
            }
        }
        
    }

    private void showGuesserInterface(MakeGuessState state) {
        roleLabel.setText("Your Role: Guesser");
        dynamicPanel.add(guesserGamePanel, BorderLayout.CENTER);
        updateGuesserGamePanel(state, true); // Enable buttons for guesser
    }

    private void showSpectatorInterface(MakeGuessState state) {
        roleLabel.setText("Your Role: Spectator");
        dynamicPanel.add(guesserGamePanel, BorderLayout.CENTER); // Show game progress
        updateGuesserGamePanel(state, false); // Disable buttons for spectator
    }

    private void showRoundEndInterface(MakeGuessState state) {
        roleLabel.setText("Round Ended!");
        dynamicPanel.add(guesserGamePanel, BorderLayout.CENTER); // Show final game state
        updateGuesserGamePanel(state, false); // Disable buttons
        
        String message;
        if (state.getRoundStatus().equals(WON)) {
            message = "Round Won! Moving to the next round!";
        } else if (state.getRoundStatus().equals(LOST)) {
            message = "Round Lost! Moving to the next round!";
        } else {
            message = "Round Ended!"; // Should not happen if WON/LOST are covered
        }
        JOptionPane.showMessageDialog(this, message, "Round Over", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateGuesserGamePanel(MakeGuessState state, boolean enableButtons) {
        // Update common game elements
        int maxAttempts = state.getMaxIncorrectGuesses(); // Get from state
        int remainingAttempts = maxAttempts - (state.getMaxIncorrectGuesses() - state.getRemainingAttempts()); // Calculate based on remaining and max attempts

        roundNumberLabel.setText("Round number: " + state.getCurrentRoundNumber());
        hangmanImagePanel.setIncorrectGuesses(state.getMaxIncorrectGuesses() - state.getRemainingAttempts());
        attemptsLabel.setText("Attempts left: " + state.getRemainingAttempts());
        wordPuzzleLabel.setText(state.getMaskedWord());
        
        // Update alphabet buttons
        updateAlphabetButtons(state.getGuessedLetters(), enableButtons);
    }

    private void updateAlphabetButtons(Set<Character> guessedLetters, boolean enableButtons) {
        if (alphabetButtonsPanel != null) {
            Component[] components = alphabetButtonsPanel.getComponents();
            for (Component component : components) {
                if (component instanceof JButton) {
                    JButton button = (JButton) component;
                    // Ensure button text is uppercase for comparison
                    char letter = button.getText().charAt(0); 
                    button.setEnabled(enableButtons && !guessedLetters.contains(letter));
                    button.setBackground(guessedLetters.contains(letter) ? Color.LIGHT_GRAY : UIManager.getColor("Button.background"));
                }
            }
        }
    }


    private JPanel createNewLetterButtonsPanel(boolean enable) {
        JPanel lettersPanel = new JPanel(new GridLayout(2, 13, 5, 5));
        lettersPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (int i = 0; i < 26; i++) {
            final char letter = (char) ('A' + i);
            JButton button = new JButton(String.valueOf(letter));
            button.setEnabled(enable); // Set initial state based on 'enable' parameter

            button.addActionListener(e -> {
                // Make a guess by the letter
                if (makeGuessController != null) {
                    makeGuessController.execute(Character.toLowerCase(letter));
                }
            });
            lettersPanel.add(button);
        }
        return lettersPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // This method is required by ActionListener, but our buttons use lambdas
    }

    public String getViewName() {
        return viewName;
    }
    
    // Setter for controller, if it's not passed in constructor
    public void setMakeGuessController(MakeGuessController controller) {
        this.makeGuessController = controller;
    }
}