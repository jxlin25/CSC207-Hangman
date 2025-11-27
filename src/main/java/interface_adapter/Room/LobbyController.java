package interface_adapter.Room;

import network.HangmanClient;
import use_case.Room.LobbyInputBoundary;
import use_case.Room.LobbyInteractor;


public class LobbyController {
    private final LobbyInputBoundary lobbyInteractor;

    public LobbyController(LobbyInteractor lobbyInteractor) {
        this.lobbyInteractor = lobbyInteractor;
    }
    public void startGame(int roomId) {
        lobbyInteractor.startGame(roomId);
    }

}
