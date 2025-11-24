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
    private final JButton backButton;




    //private final JLabel hangmanImageLabel;
    //private final JLabel messageLabel;
    //private final JTextField guessInputField;
    //private final JButton guessButton;

    private JPanel alphabetButtonsPanel;

    public MakeGuessView(MakeGuessViewModel viewModel) {

        this.alphabetButtonsPanel = this.createNewLetterButtonsPanel();

        this.makeGuessViewModel = viewModel;
        this.makeGuessViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        wordPuzzleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        backButton = new JButton("Restart");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        backButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to restart the game?",
                    "Confirm Restart",
                    JOptionPane.YES_NO_OPTION
            );
            if (option == JOptionPane.YES_OPTION) {
                returnToStartView();
            }
        });


//        hangmanImageLabel = new JLabel();
//        hangmanImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        updateImage(0);


//        messageLabel = new JLabel("Enter a letter:");
//        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

//        guessInputField = new JTextField(5);
//        guessInputField.setMaximumSize(new Dimension(100, 30));
//
//        guessButton = new JButton(MakeGuessViewModel.GUESS_BUTTON_LABEL);
//        guessButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        // Button Action
//        guessButton.addActionListener(
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        if (evt.getSource().equals(guessButton)) {
//
//                            String letter = guessInputField.getText();
//
//
//                            guessInputField.setText("");
//                        }
//                    }
//                }
//        );

        // Add everything to the panel
        this.add(hangmanImagePanel);
        this.add(attemptsLabel);
        this.add(roundNumberLabel);
        this.add(Box.createVerticalStrut(20));
//        this.add(hangmanImageLabel);
        this.add(Box.createVerticalStrut(20));
//        this.add(messageLabel);
//        this.add(guessInputField);
//        this.add(guessButton);
        this.add(wordPuzzleLabel);
        this.add(alphabetButtonsPanel);
        this.add(backButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

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

        if (!state.isGameOver()) {
            int maxAttempts = 6; // can be changed by difficulty level setting
            int remainingAttempts = state.getRemainingAttempts();

            if (state.getRoundStatus().equals(WON) || state.getRoundStatus().equals(LOST)) {
                System.out.println("triggered");

                remainingAttempts = maxAttempts;
                // renew the buttons
                this.remove(this.alphabetButtonsPanel);
                this.alphabetButtonsPanel = this.createNewLetterButtonsPanel();
                this.add(alphabetButtonsPanel);
                this.revalidate();
                this.repaint();
            }
            this.roundNumberLabel.setText("Round number: " + state.getCurrentRoundNumber());
            hangmanImagePanel.setIncorrectGuesses(maxAttempts - remainingAttempts);
            this.attemptsLabel.setText("Attempts left: " + remainingAttempts);


            // Update the displayed word
            this.wordPuzzleLabel.setText(state.getMaskedWord());
        }
        else{
            JOptionPane.showMessageDialog(
                    this,                   // parent component
                    "Game Over!",           // message
                    "Game Over",            // title
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
        if (viewManagerModel != null) {
            viewManagerModel.setState("Generate Word");
            viewManagerModel.firePropertyChange();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "viewManagerModel not set.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setMakeGuessController(MakeGuessController controller) {
        this.makeGuessController = controller;
    }

    public void setViewManagerModel(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

//    public String getViewName() {
//        return viewName;
//    }

}