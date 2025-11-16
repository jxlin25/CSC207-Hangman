package Demo;

import javax.swing.*;
import java.awt.*;

public class HintDemoView extends JPanel {

    private final String viewName = "hint demo";

    public HintDemoView() {
        this.setLayout(new BorderLayout());

        JTextField inputField = new JTextField(20);
        JButton hintButton = new JButton("Get Hint");

        hintButton.addActionListener(e -> {

        });

        JPanel centerPanel = new JPanel();
        centerPanel.add(new JLabel("Enter a word:"));
        centerPanel.add(inputField);
        centerPanel.add(hintButton);

        this.add(centerPanel);
    }

    public String getViewName() {
        return viewName;
    }
}
