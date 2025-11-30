package data_access;

public interface MultiplayerMakeGuessDataAccessInterface {
    void sendGuessToServer(String roomId, char letter);
}
