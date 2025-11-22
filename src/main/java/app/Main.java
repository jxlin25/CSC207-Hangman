package app;

import javax.swing.*;
//import use_case.MakeGuess.MakeGuessWordPuzzleDataAccessInterface;

public class Main {
    public static void main(String[] args) {

        JFrame app = new AppBuilder()
                .addGenerateWordView()
                .addMakeGuessView()
                .addGenerateWordUseCase()
                .addMakeGuessUseCase()
                .build();

        app.pack();
        app.setVisible(true);
    }
}