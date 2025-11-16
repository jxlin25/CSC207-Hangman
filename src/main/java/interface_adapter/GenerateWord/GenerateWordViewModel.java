package interface_adapter.GenerateWord;

import entity.WordPuzzle;
import interface_adapter.ViewModel;

public class GenerateWordViewModel extends ViewModel<GenerateWordState> {

    public static final String VIEW_NAME = "Generate Word";

    public GenerateWordViewModel() {
        super(VIEW_NAME);
        setState(new GenerateWordState());
    }
}

