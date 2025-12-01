package app;

import javax.swing.*;
import network.*;

public class Main {
    public static void main(String[] args) {
        HangmanServer server = new HangmanServer(8080);
        server.start();
        final JFrame app = new AppBuilder()
                .addEndGameResultsViewModel()
                .addGenerateWordView()
                .addMakeGuessView()
                .addEndGameResultsView()
                .addRoomJoinView()
                .addGenerateWordUseCase()
                .addInitializeRoundUseCase()
                .addMakeGuessUseCase()
                .addEndGameResultsUseCase()
                .addHintUseCase()
                .addChooseDifficultyUseCase()
                .build();
        app.setSize(800, 600);
        app.setLocationRelativeTo(null);
        app.setVisible(true);
    }
}


