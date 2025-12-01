package view; // Ensure this package matches your project structure

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class HangmanImagePanel extends JPanel {

    private final JLabel imageLabel = new JLabel();

    public HangmanImagePanel() {
        this.setLayout(new BorderLayout());
        this.add(imageLabel, BorderLayout.CENTER);
        // Start with 0 attempt used image loaded (assuming it's in resources/images/hangman0.png)
        setImageIndex(0);
    }

    // This method updates the image based on the number of attempts used
    public void setImageIndex(int attemptsUsed) {

        ImageIcon icon = loadImage(attemptsUsed);
        imageLabel.setIcon(icon);
    }

    private ImageIcon loadImage(int attempt) {
        String path = "/images/hangman" + attempt + ".png";
        URL imgURL = getClass().getResource(path);

        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);

            // Scale image
            Image scaled = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        }
        return null;
    }

//    // shrink panel
//    public Dimension getPreferredSize() {
//        return new Dimension(200, 200);
//    }
}