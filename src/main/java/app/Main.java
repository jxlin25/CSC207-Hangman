package app;

import javax.swing.*;
//import use_case.MakeGuess.MakeGuessWordPuzzleDataAccessInterface;
//import use_case.MakeGuess.MakeGuessWordPuzzleDataAccessInterface;
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
                .addGenerateWordUseCase()
                .addMakeGuessView()
                .addMakeGuessUseCase()
                .addRoomJoinView()
                .build();
        app.setSize(800, 600);
        app.setLocationRelativeTo(null);
        app.setVisible(true);
    }
}


