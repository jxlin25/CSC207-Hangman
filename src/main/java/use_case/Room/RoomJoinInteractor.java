package use_case.Room;
import data_access.InMemoryRoomJoinDataAccessObject;
import entity.Player;
import entity.Room;

public class RoomJoinInteractor implements RoomJoinInputBoundary {

    private final InMemoryRoomJoinDataAccessObject dao;
    private final RoomJoinOutputBoundary presenter;

    public RoomJoinInteractor(InMemoryRoomJoinDataAccessObject dao,
                              RoomJoinOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }

    @Override
    public void execute(RoomJoinInputData input) {
        int roomId = input.getRoomId();
        Player player = input.getPlayer();

        if (roomId == -1) {
            player.setHost(true);
            System.out.println("INTERACTOR: Player '" + player.getName() + "' host status set to: " + player.getHost());
            dao.createRoom(player);
        } else {
            dao.joinRoom(roomId, player);
        }

    }
}
