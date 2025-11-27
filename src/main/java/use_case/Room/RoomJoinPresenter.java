package use_case.Room;

import interface_adapter.Room.RoomJoinViewModel;

public class RoomJoinPresenter implements RoomJoinOutputBoundary {

    private final RoomJoinViewModel viewModel;

    public RoomJoinPresenter(RoomJoinViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccess(RoomJoinOutputData data) {
        viewModel.setRoomId(data.getRoomId());
        viewModel.setUsername(data.getUsername());
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFail(String error) {
        viewModel.setErrorMessage(error);
        viewModel.firePropertyChanged();
    }
}
