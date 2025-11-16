package Demo;

import Demo.HintDemoView;

import javax.swing.*;
import java.awt.*;

public class HintDemoBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private HintDemoView hintDemoView;

    public HintDemoBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public HintDemoBuilder addHintDemoView() {
        hintDemoView = new HintDemoView();
        cardPanel.add(hintDemoView, hintDemoView.getViewName());
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Hint Demo");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);
        cardLayout.show(cardPanel, hintDemoView.getViewName());

        return application;
    }
}
