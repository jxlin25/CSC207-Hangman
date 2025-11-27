package data_access;

import entity.Player;
import org.java_websocket.client.WebSocketClient;
import use_case.Room.CreateRoomDataAccessInterface;
import use_case.Room.RoomJoinInteractor;
import entity.Room;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class InMemoryRoomJoinDataAccessObject implements CreateRoomDataAccessInterface {
    private final Map<Integer, Room> rooms = new HashMap<>();
    private final WebSocketClient client;

    public InMemoryRoomJoinDataAccessObject(WebSocketClient client) {
        this.client = client;
    }

    @Override
    public boolean roomExists(int roomId) {
        return rooms.containsKey(roomId);
    }

    @Override
    public int createRoom(Player player) {
        int maxAttempts = 10;

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int roomId = (int) (Math.random() * 9000) + 1000;

            if (!rooms.containsKey(roomId)) {
                Room newRoom = new Room(roomId);
                newRoom.addUser(player);
                rooms.put(roomId, newRoom);

                return roomId;
            }
        }

        throw new RuntimeException("Failed to generate unique room ID after retries");
    }

    @Override
    public void joinRoom(int roomId, Player player) {
        String username = player.getName();
        rooms.get(roomId).addUser(player);
        client.send("User " + username + " joined room " + roomId);
    }


    public void addUser(int roomId, Player player) {
        String username = player.getName();
        rooms.get(roomId).addUser(player);
        client.send("User" + username + " joined room");
    }


    @Override
    public void sendToServer(String message) {
        client.send(message);
    }

    public Map<Integer, Room> getRooms() {
        return rooms;
    }

    public Room getRoom(int roomId) {

        for (int i = 0; i < rooms.size(); i++) {
            Room currentRoom = rooms.get(i);
            if (currentRoom.getRoomId() == roomId) {
                return currentRoom;
            }
        }
        return null;
    }
}
