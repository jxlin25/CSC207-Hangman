package interface_adapter.Room;

public class RoomJoinState {
    private String username = "";
    private String roomId = "";
    private String error = "";

    // Copy constructor
    public RoomJoinState(RoomJoinState copy) {
        this.username = copy.username;
        this.roomId = copy.roomId;
        this.error = copy.error;
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
}