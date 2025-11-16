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

public class GenerateWordView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "generate word";

    private GenerateWordController generateWordController;
    private final GenerateWordViewModel generateWordViewModel;

    private final JLabel generatedWordLabel = new JLabel("Press START to generate a word.");
    private final JButton startGameButton;

    public GenerateWordView(GenerateWordViewModel viewModel) {
        this.generateWordViewModel = viewModel;

        generateWordViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Let's Start Game!");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        startGameButton = new JButton("START");

        startGameButton.addActionListener(evt -> {
            if (evt.getSource().equals(startGameButton)) {
                generateWordController.execute();
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel center = new JPanel();
        center.add(generatedWordLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startGameButton);

        this.add(title);
        this.add(center);
        this.add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click: " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final GenerateWordState state = (GenerateWordState) evt.getNewValue();
        WordPuzzle puzzle = state.getWordPuzzle();

        if (puzzle != null) {
            String word = new String(puzzle.getLetters());
            generatedWordLabel.setText("Generated Word: " + word);
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setGenerateWordController(GenerateWordController controller) {
        this.generateWordController = controller;
    }
}
