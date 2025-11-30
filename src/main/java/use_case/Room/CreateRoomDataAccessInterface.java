package use_case.Room;

import entity.Player;

public interface CreateRoomDataAccessInterface {
    void createRoom(Player player);
    void sendToServer(String message);
    void joinRoom(int roomId, Player player);
}
