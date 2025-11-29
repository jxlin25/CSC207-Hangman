package data_access;

import interface_adapter.Room.LobbyState;
import use_case.Room.LobbyDataAccessInterface;

public class InMemoryLobbyDataAccessObject implements LobbyDataAccessInterface {
    @Override
    public void saveLobbyState(int roomId, LobbyState lobbyState) {

    }

    @Override
    public LobbyState getLobbyState(int roomId) {
        return null;
    }

    @Override
    public boolean existsLobby(int roomId) {
        return false;
    }

    @Override
    public void removeLobby(int roomId) {

    }
}
