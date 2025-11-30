package data_access;

import entity.Player;
import org.java_websocket.client.WebSocketClient;
import use_case.Room.CreateRoomDataAccessInterface;
import use_case.Room.RoomJoinInteractor;
import entity.Room;
import network.HangmanClient;

import java.util.*;

public class InMemoryRoomJoinDataAccessObject implements CreateRoomDataAccessInterface {
    private final HangmanClient client;
    private final RoomRepository roomRepository;

    public InMemoryRoomJoinDataAccessObject(HangmanClient client, RoomRepository roomRepository) {
        this.client = client;
        this.roomRepository = roomRepository;
    }

    @Override
    public void createRoom(Player player) {
        // The client suggests an ID. The server will have the final say.
        int roomId = (int) (Math.random() * 9000) + 1000;
        String message = String.format("{\"type\":\"create\", \"room\":\"%d\", \"username\":\"%s\"}", roomId, player.getName());
        client.send(message);
    }

    @Override
    public void joinRoom(int roomId, Player player) {
        String message = String.format("{\"type\":\"join\", \"room\":\"%d\", \"username\":\"%s\"}", roomId, player.getName());
        client.send(message);
    }

    @Override
    public void sendToServer(String message) {
        client.send(message);
    }
}
