package view;
import javax.swing.*;
import java.awt.*;

import interface_adapter.Room.LobbyController;
import interface_adapter.Room.LobbyViewModel;
import network.HangmanClient;
import use_case.Room.LobbyInteractor;

public class LobbyView extends JFrame {
    private LobbyViewModel lobbyViewModel;
    private LobbyController lobbyController;

    public LobbyView(LobbyViewModel lobbyViewModel, LobbyController lobbyController) {
        this.lobbyViewModel = lobbyViewModel;
        this.lobbyController = lobbyController;
    }

    private void initializeUI() {
        setTitle("Room " + roomId + " - Hangman Lobby");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Welcome to Room " + roomId +
                (createRoom ? " (Host)" : " (Player)"),
                SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        // Only show start button if user is the room creator
        if (createRoom) {
            startGameButton = new JButton("Start Game");
            //startGameButton.addActionListener(e -> startGame());
            add(startGameButton, BorderLayout.SOUTH);

            lobbyController.startGame(roomId);
        } else {
            // Show waiting message for players
            JLabel waitingLabel = new JLabel("Waiting for host to start the game...",
                    SwingConstants.CENTER);
            add(waitingLabel, BorderLayout.SOUTH);
        }
    }

    private void setupMessageHandling() {
//        client.setOnMessageListener(this::handleServerMessage);
    }

    private void handleServerMessage(String message) {
        System.out.println("Lobby received: " + message);
    }
}