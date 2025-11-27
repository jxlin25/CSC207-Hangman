package interface_adapter.Room;
import entity.Player;
import network.HangmanClient;
import use_case.Room.RoomJoinInputBoundary;
import use_case.Room.RoomJoinInputData;
import view.RoomJoinView;
import use_case.Room.RoomJoinInteractor;

import java.util.concurrent.atomic.AtomicBoolean;

public class RoomJoinController implements RoomJoinView.Controller {
    private RoomJoinInputBoundary roomJoinInputBoundary;
    @Override
    public void onJoinRoom(int roomId, String username) {
        try {
            Player player = new Player(username);
            RoomJoinInputData roomJoinInputData = new RoomJoinInputData(roomId, player);
            roomJoinInputBoundary.execute(roomJoinInputData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onCreateRoom(String username) {
        try{
            Player player = new Player(username);
            RoomJoinInputData roomJoinInputData = new RoomJoinInputData(-1, player);
            roomJoinInputBoundary.execute(roomJoinInputData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void setInputBoundary(RoomJoinInteractor interactor) {
        this.roomJoinInputBoundary = interactor;
    }
}