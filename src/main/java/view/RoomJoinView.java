package view;
import javax.swing.*;
import java.awt.*;
import interface_adapter.Room.RoomJoinController;
import use_case.Room.RoomJoinInteractor;

public class RoomJoinView extends JFrame {

    private final JTextField roomIdField = new JTextField(20);
    private final JButton joinButton = new JButton("Join Room");
    private final JLabel messageLabel = new JLabel();
    private final JButton createButton = new JButton("Create Room");
//    RoomJoinInteractor interactor = new RoomJoinInteractor();
//    RoomJoinController controller = new RoomJoinController(interactor);
    private RoomJoinInteractor interactor;

    public void RoomJoinController(RoomJoinInteractor interactor) {
        this.interactor = interactor;
    }


    // Controller interface to decouple UI from logic
    public interface Controller {
        void onJoinRoom(int roomId);
        void onCreateRoom();
    }

    private Controller controller;

    public RoomJoinView() {
        super("Join or create a Hangman Room");
        setupUI();
    }

    private void setupUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        panel.add(new JLabel("Enter Room ID:"));
        panel.add(roomIdField);
        panel.add(joinButton);
        panel.add(createButton);
        panel.add(messageLabel);

        add(panel);

//        createButton.addActionListener();

        // Button click event triggers controller method
        createButton.addActionListener(e -> controller.onCreateRoom());
        joinButton.addActionListener(e -> {
            if (controller != null) {
                int roomId;

                try {
                    String text = roomIdField.getText().trim();
                    roomId = Integer.parseInt(text);
                    controller.onJoinRoom(roomId);
                    // Use roomId here
                } catch (NumberFormatException err
                ) {
                    // Handle invalid input
                    JOptionPane.showMessageDialog(null, "Please enter a valid number");
                    roomId = 0; // or some default value
                }
            }
        });
    }

    // Allows wiring the controller from outside
    public void setController(Controller controller) {
        this.controller = controller;
    }

    // Display messages to the user (success, error, info)
    public void showMessage(String message) {
        messageLabel.setText(message);

    }

    // Optionally clear input field
    public void clearInput() {
        roomIdField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RoomJoinView view = new RoomJoinView();
            view.setVisible(true);
        });
    }
}
