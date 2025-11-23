package interface_adapter.InitializeFirstRound;

import constant.StatusConstant;
import interface_adapter.MakeGuess.MakeGuessState;
import interface_adapter.MakeGuess.MakeGuessViewModel;
import use_case.InitializeFirstRound.InitializeFirstRoundOutputBoundary;
import use_case.InitializeFirstRound.InitializeFirstRoundOutputData;

public class InitializeFirstRoundPresenter implements InitializeFirstRoundOutputBoundary {

    private final MakeGuessViewModel makeGuessViewModel;

    public InitializeFirstRoundPresenter(MakeGuessViewModel makeGuessViewModel) {

        this.makeGuessViewModel = makeGuessViewModel;
    }

    @Override
    public void initializeView(InitializeFirstRoundOutputData outputData) {

        final MakeGuessState state = makeGuessViewModel.getState();

        state.setGuessedLetter(null);
        state.setGuessCorrect(false);
        state.setRoundStatus(StatusConstant.GUESSING);
        state.setGameOver(false);
        state.setRemainingAttempts(outputData.getRemainingAttempts());
        state.setCurrentRoundNumber(outputData.getCurrentRoundNumber());
        state.setMaskedWord(outputData.getMaskedWord());

//        // If the game is over...
//        if (outputData.isGameOver()) {
//            state.setMessage("Game Over!");
//        }
//        // If the game is not over...
//        else {
//            // If the round is not ended...
//            if (outputData.getRoundStatus().equals(GUESSING)){
//
//                // If the guess is correct
//                if (outputData.isGuessCorrect()) {
//                    state.setMessage(String.format("Correct guess! %d attempts left.", outputData.getRemainingAttempts()));
//                }
//                // If the guess is incorrect
//                else if (!outputData.isGuessCorrect()) {
//                    state.setMessage(String.format("Wrong guess, try again. %d attempts left.", outputData.getRemainingAttempts()));
//                }
//
//            }
//            // If the round is ended
//            else{
//
//                // If the round ended with puzzle solved
//                if (outputData.getRoundStatus().equals(WON)) {
//                    state.setMessage("You Won this round! Moving to the next round!");
//                }
//
//                // If the round ended with puzzle unsolved
//                if (outputData.getRoundStatus().equals(LOST)) {
//                    state.setMessage("You Lost this round! Moving to the next round!");
//                }
//            }
//        }
        makeGuessViewModel.setState(state);
        makeGuessViewModel.firePropertyChanged();
    }


}
