package view;
import javax.swing.*;
import java.awt.*;
import interface_adapter.Room.RoomJoinController;
import use_case.Room.RoomJoinInteractor;

public class RoomJoinView extends JFrame {

    private final JTextField roomIdField = new JTextField(20);
    private final JTextField usernameField = new JTextField(20);
    private final JButton joinButton = new JButton("Join Room");
    private final JButton createButton = new JButton("Create Room");
    private final JLabel messageLabel = new JLabel();


    // Updated Controller interface to include username
    public interface Controller {
        void onJoinRoom(int roomId, String username);
        void onCreateRoom(String username);

        void setInputBoundary(RoomJoinInteractor interactor);
    }

    private Controller controller;

    public RoomJoinView() {
        super("Join or create a Hangman Room");
        setupUI();
    }

    private void setupUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200); // Increased height to accommodate additional row
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2)); // Changed to 4 rows, 2 columns

        // Room ID row
        panel.add(new JLabel("Room ID:"));
        panel.add(roomIdField);

        // Username row
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        // Buttons row (spanning both columns)
        panel.add(joinButton);
        panel.add(createButton);

        // Message label row (spanning both columns)
        panel.add(messageLabel);

        add(panel);

        // Button click event triggers controller method
        createButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            if (!username.isEmpty()) {
                controller.onCreateRoom(username);
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a username");
            }
            this.dispose();
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
                    this.dispose();
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid room number");
                }
            }
        });
    }

    // Allows wiring the controller from outside
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {return this.controller;}

    // Display messages to the user
    public void showMessage(String message) {
        messageLabel.setText(message);
    }

    // Optionally clear input fields
    public void clearInput() {
        roomIdField.setText("");
        usernameField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RoomJoinView view = new RoomJoinView();
            view.setVisible(true);
        });
    }
}