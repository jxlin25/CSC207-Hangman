package interface_adapter.generate_word;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Generate Word View.
 */

public class GenerateWordViewModel extends ViewModel<GenerateWordState> {

    public static final String VIEW_NAME = "Generate Word";

    public GenerateWordViewModel() {
        super(VIEW_NAME);
        setState(new GenerateWordState());
    }
}

