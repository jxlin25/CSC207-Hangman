package interface_adapter.GameSession;

import interface_adapter.ViewModel;

public class SubmitWordViewModel extends ViewModel {
    public static final String VIEW_NAME = "submit_word";

    public SubmitWordViewModel() {
        super(VIEW_NAME);
        setState(new SubmitWordState());
    }
}