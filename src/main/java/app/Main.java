package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame app = new AppBuilder()
                .addGenerateWordView()
                .addGenerateWordUseCase()
                .build();

        app.pack();
        app.setVisible(true);
    }
}