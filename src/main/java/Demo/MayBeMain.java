package Demo;

import javax.swing.*;

public class MayBeMain {
    public static void Notmain(String[] args) {
        HintDemoBuilder appBuilder = new HintDemoBuilder();
        JFrame application = appBuilder
                .addHintDemoView()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
