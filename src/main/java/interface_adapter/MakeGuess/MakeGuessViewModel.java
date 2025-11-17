package interface_adapter.MakeGuess;

import interface_adapter.ViewModel;


public class MakeGuessViewModel extends ViewModel<MakeGuessState> {
    public static final String VIEW_NAME = "Make Guess";
    public MakeGuessViewModel(String viewName) {
        super(viewName);
        setState(new MakeGuessState());
    }
}
