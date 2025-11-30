package interface_adapter.MakeGuess;

import data_access.MultiplayerMakeGuessDataAccessInterface; // New import
import entity.Guess;
import use_case.MakeGuess.MakeGuessInputBoundary;
import use_case.MakeGuess.MakeGuessInputData;

public class MakeGuessController {

    private final MakeGuessInputBoundary singleplayerInteractor; // Renamed for clarity
    private final MultiplayerMakeGuessDataAccessInterface multiplayerDataAccess; // New field
    private final MakeGuessViewModel makeGuessViewModel; // New field

    public MakeGuessController(MakeGuessInputBoundary singleplayerInteractor,
                               MultiplayerMakeGuessDataAccessInterface multiplayerDataAccess,
                               MakeGuessViewModel makeGuessViewModel) {
        this.singleplayerInteractor = singleplayerInteractor;
        this.multiplayerDataAccess = multiplayerDataAccess;
        this.makeGuessViewModel = makeGuessViewModel;
    }

    public void execute(char letter) {
        MakeGuessState state = makeGuessViewModel.getState(); // Get state to check game type
        
        if (state.isMultiplayerGame()) {
            // Multiplayer game: send guess to server
            // roomId is now correctly available in MakeGuessState
            String roomId = String.valueOf(state.getRoomId()); 
            multiplayerDataAccess.sendGuessToServer(roomId, letter);
        } else {
            // Single-player game: execute local interactor
            Guess guess = new Guess(letter);
            final MakeGuessInputData makeGuessInputData = new MakeGuessInputData(guess);
            this.singleplayerInteractor.execute(makeGuessInputData);
        }
    }
}
