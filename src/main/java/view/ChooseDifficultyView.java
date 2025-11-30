package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Simple difficulty selection screen.
 * For now, this is a pure UI + navigation component (no dedicated use case).
 */
public class ChooseDifficultyView extends JPanel implements ActionListener, PropertyChangeListener {

    public static final String VIEW_NAME = "Difficulty Selection";

    private final ViewManagerModel viewManagerModel;
    private final GenerateWordView generateWordView; // to configure attempts

    private final JButton easyButton = new JButton("Easy (8 attempts)");
    private final JButton defaultButton = new JButton("Default (6 attempts)");
    private final JButton hardButton = new JButton("Hard (4 attempts)");

    public ChooseDifficultyView(ViewManagerModel viewManagerModel, GenerateWordView generateWordView) {
        this.viewManagerModel = viewManagerModel;
        this.generateWordView = generateWordView;

        this.viewManagerModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Select Difficulty");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(20));
        add(label);
        add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(easyButton);
        buttonPanel.add(defaultButton);
        buttonPanel.add(hardButton);
        add(buttonPanel);

        easyButton.addActionListener(this);
        defaultButton.addActionListener(this);
        hardButton.addActionListener(this);
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int attempts;
        if (e.getSource() == easyButton) {
            attempts = 8;
        } else if (e.getSource() == defaultButton) {
            attempts = 6;
        } else if (e.getSource() == hardButton) {
            attempts = 4;
        } else {
            return;
        }

        // Configure GenerateWordView with the chosen attempts
        generateWordView.setSelectedAttempts(attempts);

        // Navigate to GenerateWordView
        viewManagerModel.setState(generateWordView.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // No internal state to update for now.
    }
}
