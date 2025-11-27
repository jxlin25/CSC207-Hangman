package interface_adapter.Room;

import entity.Player;
import java.util.List;

public class LobbyState {

    private int roomId;
    private List<Player> players;

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public List<Player> getPlayers() { return players; }
    public void setPlayers(List<Player> players) { this.players = players; }
}
