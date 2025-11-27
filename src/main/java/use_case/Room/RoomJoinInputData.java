package use_case.Room;

import entity.Player;
import network.HangmanClient;

public class RoomJoinInputData {
    private final Player player;
    private final int roomId;


    public RoomJoinInputData(int roomId, Player player) {
        this.player = player;
        this.roomId = roomId;

    }

    public Player getPlayer() { return player; }
    public int getRoomId() { return roomId; }
}