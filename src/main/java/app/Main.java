package app;

import javax.swing.*;
import network.*;
import view.RoomJoinView;
import interface_adapter.Room.RoomJoinController;
import use_case.Room.RoomJoinInteractor;



public class Main {
    public static void main(String[] args) {
        final int port = 8080;
        final String host = "localhost";

        if (!isServerRunning(host, port)) {
            System.out.println("No server detected. Starting a new Hangman server...");
            network.HangmanServer server = new network.HangmanServer(port);
            server.start();
        } else {
            System.out.println("Server already running. Connecting as client.");
        }

        JFrame app = new AppBuilder()
                .addGenerateWordView()
                .addMakeGuessView()
                .addLobbyView()
                .addRoomJoinView()
                .addGenerateWordUseCase()
                .addMakeGuessUseCase()
                .build();
        app.setSize(800, 600);
        app.setLocationRelativeTo(null);
        app.setVisible(true);
    }

    private static boolean isServerRunning(String host, int port) {
        try (java.net.Socket socket = new java.net.Socket()) {
            // Try to connect with a short timeout
            socket.connect(new java.net.InetSocketAddress(host, port), 1000);
            // If connection succeeds, a server is running
            return true;
        } catch (java.io.IOException e) {
            // If connection is refused, no server is running
            return false;
        }
    }
}


