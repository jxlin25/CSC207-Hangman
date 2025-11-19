package app;

import javax.swing.*;
import use_case.MakeGuess.MakeGuessWordPuzzleDataAccessInterface;
import network.*;
import view.RoomJoinView;
import interface_adapter.Room.RoomJoinController;
import use_case.Room.RoomJoinInteractor;



public class Main {
    public static void main(String[] args) {

        JFrame app = new AppBuilder()
                .addGenerateWordView()
                .addGenerateWordUseCase()
                .addMakeGuessView()
                .addMakeGuessUseCase()

                .build();

        app.pack();
        app.setVisible(true);
        HangmanServer server = new HangmanServer(8080);
        server.start();

        SwingUtilities.invokeLater(() -> {
            // Create the view
            RoomJoinView view = new RoomJoinView();

            // Create the controller with its dependencies
            RoomJoinInteractor interactor = new RoomJoinInteractor();
            RoomJoinController controller = new RoomJoinController(interactor);

            // Connect them using the setController method
            view.setController(controller);

            view.setVisible(true);
        });



    }
}



