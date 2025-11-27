package use_case.Room;

import entity.Player;
import entity.Room;

import java.util.List;
import java.util.Map;

public class RoomJoinOutputData {
    private final int roomId;
    private final String username;
    private final List<Player>  players;

    public RoomJoinOutputData(int roomId, Player player, List<Player> players) {
        this.roomId = roomId;
        this.username = player.getName();
        this.players = players;
    }

    public int getRoomId() { return roomId; }
    public String getUsername() { return username; }
    public List<Player> getPlayers() { return players; }

}