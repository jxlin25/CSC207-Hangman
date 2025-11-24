package view;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class DifficultyDialog extends JDialog {
    public enum Difficulty { EASY, MEDIUM, HARD }

    public DifficultyDialog(JFrame owner, Consumer<Difficulty> onSelection) {
        super(owner, "Select Difficulty", true);
        setLayout(new BorderLayout(10, 10));
        JLabel title = new JLabel("Choose difficulty:", SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(12, 12, 0, 12));
        add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton easy = new JButton("Easy");
        JButton medium = new JButton("Medium");
        JButton hard = new JButton("Hard");

        easy.addActionListener(e -> { onSelection.accept(Difficulty.EASY); dispose(); });
        medium.addActionListener(e -> { onSelection.accept(Difficulty.MEDIUM); dispose(); });
        hard.addActionListener(e -> { onSelection.accept(Difficulty.HARD); dispose(); });

        panel.add(easy); panel.add(medium); panel.add(hard);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 12, 12, 12));
        add(panel, BorderLayout.CENTER);

        setPreferredSize(new Dimension(360, 140));
        pack();
        setLocationRelativeTo(owner);
    }
}
