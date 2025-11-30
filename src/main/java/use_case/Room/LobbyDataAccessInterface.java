package use_case.Room;

import interface_adapter.Room.LobbyState;

public interface LobbyDataAccessInterface {
    void saveLobbyState(int roomId, LobbyState lobbyState);
    LobbyState getLobbyState(int roomId);
    boolean existsLobby(int roomId);
    void removeLobby(int roomId);
    boolean sendStartGameRequest(int roomId);
}