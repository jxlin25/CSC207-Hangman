package view;

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

public class MakeGuessView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "Make Guess";
    private final MakeGuessViewModel makeGuessViewModel;
    private MakeGuessController makeGuessController;


    private final JLabel hangmanImageLabel;
    private final JLabel messageLabel;
    private final JTextField guessInputField;
    private final JButton guessButton;

    public MakeGuessView(MakeGuessViewModel viewModel) {
        this.makeGuessViewModel = viewModel;
        this.makeGuessViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        hangmanImageLabel = new JLabel();
        hangmanImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateImage(0);


        messageLabel = new JLabel("Enter a letter:");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        guessInputField = new JTextField(5);
        guessInputField.setMaximumSize(new Dimension(100, 30));

        guessButton = new JButton(MakeGuessViewModel.GUESS_BUTTON_LABEL);
        guessButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button Action
        guessButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(guessButton)) {

                            String letter = guessInputField.getText();


                            guessInputField.setText("");
                        }
                    }
                }
        );

        // Add everything to the panel
        this.add(Box.createVerticalStrut(20));
        this.add(hangmanImageLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(messageLabel);
        this.add(guessInputField);
        this.add(guessButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        MakeGuessState state = (MakeGuessState) evt.getNewValue();


        messageLabel.setText(state.getMessage());


        int maxAttempts = 6;
        int attemptsLeft = 6;



    }
    public void updateImage(int mistakes) {

    }

    public String getViewName() {
        return viewName;
    }

}