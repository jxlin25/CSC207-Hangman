package interface_adapter.Room;

import interface_adapter.ViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.Room.LobbyViewModel;
import use_case.Room.RoomJoinOutputBoundary;
import use_case.Room.RoomJoinOutputData;

public class RoomJoinPresenter implements RoomJoinOutputBoundary {

    private final RoomJoinViewModel roomJoinViewModel;
    private final LobbyViewModel lobbyViewModel;
    private final ViewManagerModel viewManagerModel;

    public RoomJoinPresenter(RoomJoinViewModel roomJoinViewModel,
                             LobbyViewModel lobbyViewModel,
                             ViewManagerModel viewManagerModel) {
        this.roomJoinViewModel = roomJoinViewModel;
        this.lobbyViewModel = lobbyViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    public void present(RoomJoinOutputData roomJoinOutputData) {

    }

    @Override
    public void prepareSuccess(RoomJoinOutputData outputData) {
        LobbyState lobbyState = lobbyViewModel.getState();
        lobbyState.setRoomId(outputData.getRoomId());
        lobbyState.setPlayers(outputData.getPlayers());
        lobbyViewModel.firePropertyChange();

        viewManagerModel.setViewName(lobbyViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFail(String error) {

        RoomJoinState state = roomJoinViewModel.getState();
        state.setError(error);
        roomJoinViewModel.setState(state);
        roomJoinViewModel.firePropertyChange();
    }
}