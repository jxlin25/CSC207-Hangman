package Demo;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        HintDemoBuilder appBuilder = new HintDemoBuilder();
        JFrame application = appBuilder
                .addHintDemoView()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
