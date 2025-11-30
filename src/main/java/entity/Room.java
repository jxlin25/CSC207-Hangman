package entity;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private final int roomId;
    private final List<Player> users = new ArrayList<>();

    public Room(int roomId) {
        this.roomId = roomId; 
    }

    public int getRoomId() {
        return roomId;
    }

    public void addUser(Player player) {
        users.add(player);
    }

    public List<Player> getUsers() {
        return users;
    }
    public Player getRoomHost() {
        if (!users.isEmpty()) {
            return users.getFirst();
        }
        return null;
    }
}
