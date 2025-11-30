package data_access;

import entity.Player;
import entity.Room;

import java.util.HashMap;
import java.util.Map;

public class RoomRepository {
    private final Map<Integer, Room> rooms = new HashMap<>();

    public boolean roomExists(int roomId) {
        return rooms.containsKey(roomId);
    }

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

    public void addUser(int roomId, Player player) {
        rooms.get(roomId).addUser(player);
    }

    public Room getRoom(int roomId) {
        return rooms.get(roomId);
    }

    public void saveRoom(Room room) {
        rooms.put(room.getRoomId(), room);
    }

    public void removeRoom(int roomId) {
        rooms.remove(roomId);
    }
}
