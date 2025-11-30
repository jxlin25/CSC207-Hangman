package view;

import interface_adapter.SubmitWord.SubmitWordController;
import interface_adapter.SubmitWord.SubmitWordState;
import interface_adapter.SubmitWord.SubmitWordViewModel;

import javax.swing.*;
import java.awt.*;

public class SubmitWordView extends JPanel {
    private final SubmitWordController controller;
    private final SubmitWordViewModel viewModel;

    private JTextField wordField;
    private JButton submitButton;
    private JLabel errorLabel;

    public SubmitWordView(SubmitWordController controller, SubmitWordViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;

        initializeUI();
        setupListeners();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Enter word for opponent to guess:"));
        wordField = new JTextField(15);
        inputPanel.add(wordField);

        submitButton = new JButton("Submit Word");
        inputPanel.add(submitButton);

        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);

        add(inputPanel, BorderLayout.CENTER);
        add(errorLabel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        submitButton.addActionListener(e -> {
            String word = wordField.getText().trim();
            if (!word.isEmpty()) {
                // Get roomId from ViewModel's state
                String roomId = viewModel.getState().getRoomId(); // Access roomId from state
                if (roomId != null && !roomId.isEmpty()) {
                    controller.execute(roomId, word);
                } else {
                    errorLabel.setText("Error: Room ID not set.");
                }
            } else {
                errorLabel.setText("Please enter a word.");
            }
        });

        // Listen for view model changes
        viewModel.addPropertyChangeListener(evt -> {
            SubmitWordState state = (SubmitWordState) viewModel.getState(); // Cast to SubmitWordState
            if (state.getError() != null) {
                errorLabel.setText(state.getError());
            } else if (state.getSuccessMessage() != null) {
                errorLabel.setText(state.getSuccessMessage());
                errorLabel.setForeground(Color.GREEN);
                // Optionally clear field or disable input
                wordField.setEnabled(false);
                submitButton.setEnabled(false);
            }
        });
    }
}