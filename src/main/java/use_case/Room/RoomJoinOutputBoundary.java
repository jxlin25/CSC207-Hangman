package use_case.Room;
public interface RoomJoinOutputBoundary {
    void prepareSuccess(RoomJoinOutputData data);
    void prepareFail(String error);
    void present(RoomJoinOutputData roomJoinOutputData);
}
