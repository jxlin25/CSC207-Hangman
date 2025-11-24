package view;

import interface_adapter.GenerateWord.GenerateWordController;
import interface_adapter.GenerateWord.GenerateWordViewModel;
import interface_adapter.InitializeRound.InitializeRoundController;

import javax.swing.*;
import java.awt.*;

public class GenerateWordView extends JPanel {
    private final String viewName = "Generate Word";

    private GenerateWordController generateWordController;
    private final GenerateWordViewModel generateWordViewModel;
    private InitializeRoundController initializeRoundController;

    private final JButton startGameButton;

    public GenerateWordView(GenerateWordViewModel viewModel) {
        this.generateWordViewModel = viewModel;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JLabel title = new JLabel("Let's Start Game!");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        startGameButton = new JButton("START");
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startGameButton.addActionListener(evt -> {
            if (evt.getSource().equals(startGameButton)) {
                generateWordController.execute();// Generate the HangmanGame entity for the game
                initializeRoundController.execute();// Update the data of the first word to the view
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startGameButton);

        this.add(title);
        this.add(buttonPanel);
    }

    public String getViewName() {
        return viewName;
    }

    public void setGenerateWordController(GenerateWordController generateWordController) {
        this.generateWordController = generateWordController;
    }

    public void setInitializeFirstRoundController(InitializeRoundController initializeRoundController) {
        this.initializeRoundController = initializeRoundController;
    }
}
