package view;
import javax.swing.*;
import java.awt.*;
import interface_adapter.Room.RoomJoinController;
import interface_adapter.Room.RoomJoinState;
import interface_adapter.Room.RoomJoinViewModel;
import use_case.Room.RoomJoinInteractor;

public class RoomJoinView extends JPanel {

    private final JTextField roomIdField = new JTextField(20);
    private final JTextField usernameField = new JTextField(20);
    private final JButton joinButton = new JButton("Join Room");
    private final JButton createButton = new JButton("Create Room");
    private final JLabel messageLabel = new JLabel();
    private RoomJoinViewModel roomJoinViewModel;
    private RoomJoinController controller;


    public RoomJoinView(RoomJoinViewModel roomJoinViewModel) {
        this.roomJoinViewModel = roomJoinViewModel;
        setupUI();
        roomJoinViewModel.addPropertyChangeListener(evt -> {
            RoomJoinState state = roomJoinViewModel.getState();
            messageLabel.setText(state.getError());
        });
    }

    private void setupUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(new JLabel("Room ID:"));
        panel.add(roomIdField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(joinButton);
        panel.add(createButton);
        panel.add(messageLabel);
        add(panel);

        createButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            if (!username.isEmpty()) {
                controller.onCreateRoom(username);
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a username");
            }
        });

        joinButton.addActionListener(e -> {
            if (controller != null) {
                int roomId;
                String username = usernameField.getText().trim();

                try {
                    String text = roomIdField.getText().trim();
                    roomId = Integer.parseInt(text);

                    if (!username.isEmpty()) {
                        controller.onJoinRoom(roomId, username);
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter a username");
                    }
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid room number");
                }
            }
        });
    }

    public void setController(RoomJoinController controller) {
        this.controller = controller;
    }

    public RoomJoinController getController() {return this.controller;}

    public void showMessage(String message) {
        messageLabel.setText(message);
    }

    public RoomJoinViewModel getRoomJoinViewModel() {return roomJoinViewModel;}

    public void clearInput() {
        roomIdField.setText("");
        usernameField.setText("");
    }

}