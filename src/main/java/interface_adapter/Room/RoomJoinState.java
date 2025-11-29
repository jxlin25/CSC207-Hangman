package interface_adapter.Room;

import entity.Player;

import java.util.List;

public class RoomJoinState {
    private String username = "";
    private String roomId = "";
    private String error = "";
    private List<Player> players;

    // Copy constructor
    public RoomJoinState(RoomJoinState copy) {
        this.username = copy.username;
        this.roomId = copy.roomId;
        this.error = copy.error;
        this.players = copy.players;
    }

    // Default constructor
    public RoomJoinState() {}

    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public List<Player> getPlayers() {return players;}
    public void setPlayers(List<Player> players) {this.players = players;}
}