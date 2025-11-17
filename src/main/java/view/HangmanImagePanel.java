package view; // Ensure this package matches your project structure

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class HangmanImagePanel extends JPanel {

    private final JLabel imageLabel = new JLabel();

    public HangmanImagePanel() {
        this.setLayout(new BorderLayout());
        this.add(imageLabel, BorderLayout.CENTER);
        // Start with 0 mistakes image loaded (assuming it's in resources/images/hangman0.png)
        setIncorrectGuesses(0);
    }

    // This method updates the image based on the number of mistakes
    public void setIncorrectGuesses(int guesses) {
        ImageIcon icon = loadImage(guesses);
        imageLabel.setIcon(icon);
    }

    private ImageIcon loadImage(int mistakes) {
        // Path should be like /images/hangman0.png
        String path = "/images/hangman" + mistakes + ".png";

        URL imgURL = getClass().getResource(path);

        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Error: Image not found at " + path);
            // Return a placeholder or null if the file isn't found
            return null;
        }
    }
}