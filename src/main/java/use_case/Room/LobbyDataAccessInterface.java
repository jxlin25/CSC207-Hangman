package use_case.Room;

import entity.game_session.LobbyState;

public interface LobbyDataAccessInterface {
    void saveLobbyState(int roomId, LobbyState lobbyState);
    LobbyState getLobbyState(int roomId);
    boolean existsLobby(int roomId);
    void removeLobby(int roomId);
}