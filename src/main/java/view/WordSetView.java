package view;

import interface_adapter.SubmitWord.SubmitWordController;
import interface_adapter.SubmitWord.SubmitWordState;
import interface_adapter.SubmitWord.SubmitWordViewModel;

import javax.swing.*;
import java.awt.*;

public class WordSetView extends JPanel {
    public final String viewName = "wordSet";

    private final SubmitWordController controller;
    private final SubmitWordViewModel viewModel;

    private JTextField wordField;
    private JButton submitButton;
    private JLabel errorLabel;
    private String currentRoomId; // This needs to be set externally or fetched from a ViewModel

    public WordSetView(SubmitWordController controller, SubmitWordViewModel viewModel) {
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
                // Get roomId from somewhere (likely from a parent view/model)
                // For now, using a placeholder. This should come from the state/viewmodel.
                if (currentRoomId != null && !currentRoomId.isEmpty()) {
                    controller.execute(currentRoomId, word);
                } else {
                    errorLabel.setText("Error: Room ID not set.");
                }
            } else {
                errorLabel.setText("Please enter a word.");
            }
        });

        // Listen for view model changes
        viewModel.addPropertyChangeListener(evt -> {
            SubmitWordState state = viewModel.getState();
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

    // Method to set the current room ID, called by the ViewManager or a parent component
    public void setCurrentRoomId(String roomId) {
        this.currentRoomId = roomId;
    }
}
