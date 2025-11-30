package view;

import interface_adapter.EndGameResults.EndGameResultsController;
import interface_adapter.GenerateWord.GenerateWordController;
import interface_adapter.GenerateWord.GenerateWordViewModel;
import interface_adapter.InitializeRound.InitializeRoundController;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * View for configuring and starting a Hangman game.
 * Now includes difficulty selection (previously handled in ChooseDifficultyView).
 */
public class GenerateWordView extends JPanel implements PropertyChangeListener {

    // Keep the original view name so ViewManagerModel and other code still work.
    private final String viewName = "Generate Word";

    private final GenerateWordViewModel generateWordViewModel;

    private GenerateWordController generateWordController;
    private InitializeRoundController initializeRoundController;
    private ViewManagerModel viewManagerModel; // optional, only if you still use view switching

    private final JButton startGameButton;
    private final JComboBox<Integer> numberSelector;

    // New: difficulty selection via dropdown
    private final JComboBox<String> difficultySelector;

    // Attempts are still configurable from outside, but by default we
    // bind them to the difficulty dropdown selection.
    private int selectedAttempts = 6; // default if no difficulty is chosen

    public GenerateWordView(GenerateWordViewModel viewModel) {
        this.generateWordViewModel = viewModel;
        this.generateWordViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JLabel title = new JLabel("Let's Start Game!");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== Difficulty selector (dropdown) =====
        // Options text matches old ChooseDifficultyView:
        // - Easy (8 attempts)
        // - Default (6 attempts)
        // - Hard (4 attempts)
        difficultySelector = new JComboBox<>(new String[]{"Easy (8 attempts)", "Default (6 attempts)", "Hard (4 attempts)"});
        difficultySelector.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficultySelector.setSelectedIndex(1);
        // "Default (6 attempts)"

        JLabel difficultyLabel = new JLabel("Select Difficulty:");
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Whenever difficulty changes, update selectedAttempts to match.
        difficultySelector.addActionListener(e -> {
            String selection = (String) difficultySelector.getSelectedItem();
            if (selection == null) {
                selectedAttempts = 6;
                // fallback
                return;
            }

            if (selection.startsWith("Easy")) {
                selectedAttempts = 8;
            } else if (selection.startsWith("Default")) {
                selectedAttempts = 6;
            } else if (selection.startsWith("Hard")) {
                selectedAttempts = 4;
            } else {
                selectedAttempts = 6;
            }
        });

        // Number of words selector
        numberSelector = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6});
        numberSelector.setAlignmentX(Component.CENTER_ALIGNMENT);
        numberSelector.setSelectedItem(1);

        JLabel numberLabel = new JLabel("Number of Words:");
        numberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Start button
        startGameButton = new JButton("START");
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startGameButton.addActionListener(evt -> {
            if (evt.getSource().equals(startGameButton)) {
                int numberOfWords = (Integer) numberSelector.getSelectedItem();

                // NOTE:
                // - Still uses generateWordController and initializeRoundController as before
                generateWordController.execute(numberOfWords);
                // Generate the HangmanGame entity for the game
                initializeRoundController.execute();
                // Update the data of the first word to the view
            }
        });

        // ===== Layout panels =====
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel difficultyPanel = new JPanel();
        difficultyPanel.add(difficultyLabel);
        difficultyPanel.add(difficultySelector);

        JPanel numberPanel = new JPanel();
        numberPanel.add(numberLabel);
        numberPanel.add(numberSelector);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startGameButton);

        this.add(title);
        this.add(Box.createVerticalStrut(15));
        this.add(difficultyPanel);
        this.add(Box.createVerticalStrut(10));
        this.add(numberPanel);
        this.add(Box.createVerticalStrut(10));
        this.add(buttonPanel);
    }

    public String getViewName() {
        return viewName;
    }

    public void setGenerateWordController(GenerateWordController generateWordController) {
        this.generateWordController = generateWordController;
    }

    public void setInitializeRoundController(InitializeRoundController initializeRoundController) {
        this.initializeRoundController = initializeRoundController;
    }

    /**
     * Optional: if you still navigate between views using ViewManagerModel,
     * you can inject it here. Not strictly required for difficulty selection.
     */
    public void setViewManagerModel(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Called from elsewhere (e.g., if a use case wants to override attempts).
     * This method is kept so existing code that calls it will still compile
     * and behave as expected.
     */
    public void setSelectedAttempts(int selectedAttempts) {
        this.selectedAttempts = selectedAttempts;

        // Also sync the dropdown, if possible, so the UI reflects this external change.
        if (selectedAttempts == 8) {
            difficultySelector.setSelectedItem("Easy (8 attempts)");
        } else if (selectedAttempts == 6) {
            difficultySelector.setSelectedItem("Default (6 attempts)");
        } else if (selectedAttempts == 4) {
            difficultySelector.setSelectedItem("Hard (4 attempts)");
        }
    }

    /**
     * Getter if other parts of the system need the chosen attempts.
     */
    public int getSelectedAttempts() {
        return selectedAttempts;
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