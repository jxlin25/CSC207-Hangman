package app;

import javax.swing.*;
import network.*;
import view.RoomJoinView;
import interface_adapter.Room.RoomJoinController;
import use_case.Room.RoomJoinInteractor;

public class Main {
    public static void main(String[] args) {
        HangmanServer server = new HangmanServer(8080);
        server.start();
        JFrame app = new AppBuilder()
                .addGenerateWordView()
                .addMakeGuessView()
                .addEndGameResultsViewModel()
                .addEndGameResultsView()
                .addRoomJoinView()
                .addGenerateWordUseCase()
                .addInitializeFirstRoundUseCase() // Please ensure addInitializeFirstRoundUseCase() is after addGenerateWordView()
                .addMakeGuessUseCase()
                .build();
        app.setSize(800, 600);
        app.setLocationRelativeTo(null);
        app.setVisible(true);
    }
}


