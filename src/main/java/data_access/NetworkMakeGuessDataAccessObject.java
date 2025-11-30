package data_access;

import network.HangmanClient;

public class NetworkMakeGuessDataAccessObject implements MultiplayerMakeGuessDataAccessInterface {
    private final HangmanClient client;

    public NetworkMakeGuessDataAccessObject(HangmanClient client) {
        this.client = client;
    }

    @Override
    public void sendGuessToServer(String roomId, char letter) {
        String message = String.format(
            "{\"type\":\"make_guess\",\"room\":\"%s\",\"username\":\"%s\",\"letter\":\"%c\"}",
            roomId, client.getPlayerId(), letter
        );
        client.send(message);
    }
}
