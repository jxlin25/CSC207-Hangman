package use_case.Room;
import entity.Player;
import interface_adapter.Room.LobbyState;


public interface LobbyInputBoundary {
    void startGame(int roomId);
    void addPlayerToLobby(String roomId, Player player);
    void removePlayerFromLobby(String roomId, String playerId);
    void setPlayerReadyStatus(String roomId, String playerId, boolean isReady);
    LobbyState getLobbyState(String roomId);

}
