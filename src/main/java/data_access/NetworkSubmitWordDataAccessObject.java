package data_access;

import network.HangmanClient;

public class NetworkSubmitWordDataAccessObject implements SubmitWordDataAccessInterface {
    private final HangmanClient client;

    public NetworkSubmitWordDataAccessObject(HangmanClient client) {
        this.client = client;
    }

    @Override
    public void submitWord(String roomId, String word) {
        String message = String.format(
            "{\"type\": \"submit_word\", \"room\": \"%s\", \"username\": \"%s\", \"word\": \"%s\"}",
            roomId, client.getPlayerId(), word
        );
        client.send(message);
    }
}