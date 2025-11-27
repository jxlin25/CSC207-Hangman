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

    public void present(RoomJoinOutputData roomJoinOutputData) {}

    @Override
    public void prepareSuccess(RoomJoinOutputData outputData) {
        // On successful room join, switch to lobby view
        lobbyViewModel.setRoomId(outputData.getRoomId());
        lobbyViewModel.setPlayers(outputData.getPlayers()); // If you have player list
        lobbyViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(lobbyViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFail(String error) {
        // Update RoomJoinViewModel with error message
        RoomJoinState currentState = roomJoinViewModel.getState();
        currentState.setError(error);
        roomJoinViewModel.setState(currentState);
        roomJoinViewModel.firePropertyChanged();
    }
}