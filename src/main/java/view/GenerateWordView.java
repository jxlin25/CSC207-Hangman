package view;

import entity.WordPuzzle;
import interface_adapter.GenerateWord.GenerateWordController;
import interface_adapter.GenerateWord.GenerateWordState;
import interface_adapter.GenerateWord.GenerateWordViewModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GenerateWordView extends JPanel {
    private final String viewName = "Generate Word";

    private GenerateWordController generateWordController;
    private final GenerateWordViewModel generateWordViewModel;

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
                generateWordController.execute();
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

    public void setGenerateWordController(GenerateWordController controller) {
        this.generateWordController = controller;
    }
}
