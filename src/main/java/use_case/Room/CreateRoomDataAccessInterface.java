package use_case.Room;

import entity.Player;

public interface CreateRoomDataAccessInterface {
    boolean roomExists(int roomId);
    int createRoom(Player player);
    void sendToServer(String message);
    void joinRoom(int roomId, Player player);
}
