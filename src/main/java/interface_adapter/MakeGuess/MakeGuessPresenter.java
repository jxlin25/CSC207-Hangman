package interface_adapter.MakeGuess;

import use_case.MakeGuess.MakeGuessOutputBoundary;
import use_case.MakeGuess.MakeGuessOutputData;

import static Constant.StatusConstant.*;

/** The Presenter for the MakeGuess use case.
 */
public class MakeGuessPresenter implements MakeGuessOutputBoundary {

    private final MakeGuessViewModel makeGuessViewModel;

    public MakeGuessPresenter(MakeGuessViewModel makeGuessViewModel) {
        this.makeGuessViewModel = makeGuessViewModel;
    }

    @Override
    public void updateView(MakeGuessOutputData outputData) {

        MakeGuessState state = makeGuessViewModel.getState();


        state.setGuessedLetter(String.valueOf(outputData.getGuess().getLetter()));
        state.setGuessCorrect(outputData.isGuessCorrect());
        state.setRoundStatus(outputData.getRoundStatus());
        state.setGameOver(outputData.isGameOver());
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
