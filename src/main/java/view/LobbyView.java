package view;
import javax.swing.*;
import java.awt.*;

import interface_adapter.Room.LobbyController;
import interface_adapter.Room.LobbyState;
import interface_adapter.Room.LobbyViewModel;

import interface_adapter.Room.RoomJoinState;
import network.HangmanClient;

public class LobbyView extends JPanel {
    private LobbyViewModel lobbyViewModel;
    private LobbyController lobbyController;
    private final JLabel messageLabel = new JLabel();
    private final JLabel roomInfoLabel = new JLabel();
    private JButton startGameButton;

    public LobbyView(LobbyViewModel lobbyViewModel) {
        this.lobbyViewModel = lobbyViewModel;
        setupUI();
        lobbyViewModel.addPropertyChangeListener(evt -> {
            LobbyState state = lobbyViewModel.getState();
            messageLabel.setText(String.valueOf(state.getRoomId()));
            updateView(state);
        });

    }

    public void setLobbyController(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

    public LobbyViewModel getLobbyViewModel() {
        return lobbyViewModel;
    }

    public LobbyController getLobbyController() {
        return lobbyController;
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        roomInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(roomInfoLabel, BorderLayout.NORTH);

        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(messageLabel, BorderLayout.CENTER);
        startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(e -> {
            if (lobbyController != null) {
                lobbyController.startGame(lobbyViewModel.getState().getRoomId());
            }
        });
        startGameButton.setVisible(false);
        add(startGameButton, BorderLayout.SOUTH);
    }
    private void updateView(LobbyState state) {
        roomInfoLabel.setText("Room ID: " + state.getRoomId());
        messageLabel.setText("Welcome!");

        if (startGameButton != null) {
            startGameButton.setVisible(state.getPlayers().getFirst().getHost());
        }
    }
}