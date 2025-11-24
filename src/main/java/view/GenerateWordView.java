package view;

import interface_adapter.GenerateWord.GenerateWordController;
import interface_adapter.GenerateWord.GenerateWordViewModel;
import interface_adapter.InitializeFirstRound.InitializeFirstRoundController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GenerateWordView extends JPanel implements PropertyChangeListener {
    private final String viewName = "Generate Word";

    private GenerateWordController generateWordController;
    private final GenerateWordViewModel generateWordViewModel;
    private InitializeFirstRoundController initializeFirstRoundController;

    private final JButton startGameButton;
    private final JComboBox<Integer> numberSelector;

    public GenerateWordView(GenerateWordViewModel viewModel) {
        this.generateWordViewModel = viewModel;
        this.generateWordViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JLabel title = new JLabel("Let's Start Game!");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        numberSelector = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6});
        numberSelector.setAlignmentX(Component.CENTER_ALIGNMENT);
        numberSelector.setSelectedItem(1);

        JLabel numberLabel = new JLabel("Number of Words:");
        numberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        startGameButton = new JButton("START");
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startGameButton.addActionListener(evt -> {
            if (evt.getSource().equals(startGameButton)) {
                int numberOfWords = (Integer) numberSelector.getSelectedItem();
                generateWordController.execute(numberOfWords);
                initializeFirstRoundController.execute();
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel numberPanel = new JPanel();
        numberPanel.add(numberLabel);
        numberPanel.add(numberSelector);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startGameButton);

        this.add(title);
        this.add(numberPanel);
        this.add(buttonPanel);
    }

    public String getViewName() {
        return viewName;
    }

    public void setGenerateWordController(GenerateWordController generateWordController) {
        this.generateWordController = generateWordController;
    }

    public void setInitializeFirstRoundController(InitializeFirstRoundController initializeFirstRoundController) {
        this.initializeFirstRoundController = initializeFirstRoundController;
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
