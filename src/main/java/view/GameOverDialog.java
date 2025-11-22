package view;

import javax.swing.;
import java.awt.;
import java.util.Objects;

public class GameOverDialog extends JDialog {
    public GameOverDialog(JFrame owner, String message, Runnable onPlayAgain, Runnable onQuit) {
        super(owner, "Game Over", true);
        setLayout(new BorderLayout(10, 10));

        JLabel msg = new JLabel(message, SwingConstants.CENTER);
        msg.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        add(msg, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        JButton playAgain = new JButton("Play Again");
        JButton quit = new JButton("Quit");

        playAgain.addActionListener(e -> {
            if (onPlayAgain != null) onPlayAgain.run();
        });
        quit.addActionListener(e -> {
            if (onQuit != null) onQuit.run();
        });

        buttons.add(playAgain);
        buttons.add(quit);
        add(buttons, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(380, 160));
        pack();
        setLocationRelativeTo(owner);
    }
}
