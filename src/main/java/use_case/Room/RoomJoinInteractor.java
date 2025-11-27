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
            roomId = dao.createRoom(player);
        } else {
            if (!dao.roomExists(roomId)) {
                presenter.prepareFail("Room does not exist");
                return;
            }

            dao.addUser(roomId, player);
        }
        Room currentRoom = dao.getRoom(roomId);
        RoomJoinOutputData roomJoinOutputData = new RoomJoinOutputData(roomId, input.getPlayer(), currentRoom.getUsers());
        presenter.present(roomJoinOutputData);
    }
}
