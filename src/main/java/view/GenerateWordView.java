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

    private final HangmanImagePanel imagePanel = new HangmanImagePanel();

    private final JLabel numOfDashesLabel;

    private GenerateWordController generateWordController;
    private final GenerateWordViewModel generateWordViewModel;

    private final JButton startGameButton;

    public GenerateWordView(GenerateWordViewModel viewModel) {
        this.generateWordViewModel = viewModel;
        viewModel.addPropertyChangeListener(this);


        WordPuzzle initialPuzzle = viewModel.getState().getWordPuzzle();

        String initialMaskedWord = (initialPuzzle != null)
                ? initialPuzzle.getMaskedWord()
                : "Press START to generate a word.";

        this.numOfDashesLabel = new JLabel(initialMaskedWord);

        Font currentFont = this.numOfDashesLabel.getFont();
        Font largerFont = new Font(currentFont.getName(), Font.BOLD, 36);
        this.numOfDashesLabel.setFont(largerFont);

        final JLabel title = new JLabel("Let's Start Game!");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 28));

        startGameButton = new JButton("START");
        startGameButton.addActionListener(evt -> {
            if (evt.getSource().equals(startGameButton)) {
                generateWordController.execute();
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel dashPanel = new JPanel();
        dashPanel.add(this.numOfDashesLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startGameButton);

        this.add(Box.createVerticalStrut(20));
        this.add(title);
        this.add(Box.createVerticalStrut(20));
        this.add(this.imagePanel);
        this.add(Box.createVerticalStrut(20));
        this.add(dashPanel);
        this.add(Box.createVerticalStrut(20));
        this.add(buttonPanel);
<<<<<<< HEAD
        this.add(Box.createVerticalGlue()); // Push content to center
=======
        this.add(imagePanel);
>>>>>>> 947e2ecd6a3967df3e734f01f8b956197f8cf13d
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
            String maskedWord = puzzle.getMaskedWord();
            this.numOfDashesLabel.setText(maskedWord);

        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setGenerateWordController(GenerateWordController controller) {
        this.generateWordController = controller;
    }
}
