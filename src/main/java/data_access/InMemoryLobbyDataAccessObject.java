package data_access;

import entity.Room;
import interface_adapter.Room.LobbyState;
import network.HangmanClient;
import use_case.Room.LobbyDataAccessInterface;

public class InMemoryLobbyDataAccessObject implements LobbyDataAccessInterface {

    private final RoomRepository roomRepository;
    private final HangmanClient client;

    public InMemoryLobbyDataAccessObject(RoomRepository roomRepository, HangmanClient client) {
        this.roomRepository = roomRepository;
        this.client = client;
    }

    @Override
    public void saveLobbyState(int roomId, LobbyState lobbyState) {

    }

    @Override
    public LobbyState getLobbyState(int roomId) {
        Room room = roomRepository.getRoom(roomId);
        if (room == null) {
            return null;
        }
        LobbyState lobbyState = new LobbyState();
        lobbyState.setRoomId(room.getRoomId());
        lobbyState.setPlayers(room.getUsers());
        return lobbyState;
    }

    @Override
    public boolean existsLobby(int roomId) {
        return roomRepository.roomExists(roomId);
    }

    @Override
    public void removeLobby(int roomId) {
        roomRepository.removeRoom(roomId);
    }

    @Override
    public boolean sendStartGameRequest(int roomId) {
        try {
            String message = String.format("{\"type\":\"START_GAME\", \"roomId\":%d}", roomId);
            client.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
