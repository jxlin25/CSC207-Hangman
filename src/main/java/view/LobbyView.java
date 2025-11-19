package view;
import javax.swing.*;
import java.awt.*;
import network.HangmanClient;

public class LobbyView extends JFrame {
    private int roomId;
    private HangmanClient client;
    private Boolean createRoom;
    private JButton startGameButton;
    private String username;

    public LobbyView(int roomId, HangmanClient client, Boolean createRoom, String username) {
        this.roomId = roomId;
        this.client = client;
        this.createRoom = createRoom;
        this.username = username;
        initializeUI();
        setupMessageHandling();
        System.out.println(username);
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