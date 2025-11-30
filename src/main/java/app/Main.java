package app;

import javax.swing.*;
import network.*;

public class Main {
    public static void main(String[] args) {
        HangmanServer server = new HangmanServer(8080);
        server.start();
        JFrame app = new AppBuilder()
                .addEndGameResultsViewModel()
                .addGenerateWordView()
                .addMakeGuessView()
//                .addDifficultySelectionView()
                .addEndGameResultsView()

                .addRoomJoinView()
                .addGenerateWordUseCase()
                .addInitializeRoundUseCase() // Please ensure addInitializeFirstRoundUseCase() is after addGenerateWordView()
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


