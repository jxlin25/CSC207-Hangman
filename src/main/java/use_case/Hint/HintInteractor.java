package use_case.Hint;

import use_case.MakeGuess.HangmanGameDataAccessInterface;

/**
 * The Generate Word Interactor.
 */

public class HintInteractor implements HintInputBoundary {
    private final HintDataAccessInterface hintDataAccessInterface;
    private final HintOutputBoundary hintOutputBoundary;
    private final HangmanGameDataAccessInterface hangmanGameDataAccessInterface;

    public HintInteractor(HintDataAccessInterface hintDataAccessInterface,
                          HintOutputBoundary hintOutputBoundary,
                          HangmanGameDataAccessInterface hangmanGameDataAccessInterface) {
        this.hintDataAccessInterface = hintDataAccessInterface;
        this.hintOutputBoundary = hintOutputBoundary;
        this.hangmanGameDataAccessInterface = hangmanGameDataAccessInterface;
    }

    @Override
    public void execute() {
        String word = new String(hangmanGameDataAccessInterface.getCurrentWordPuzzle().getLetters());
        String hint = "";
        if (hintDataAccessInterface.isApiKeyValid()) {
            hint = hintDataAccessInterface.getGemiHint(word);
        }
        else {
            String dictHint = hintDataAccessInterface.getDictHint(word);
            if (!(dictHint == null || dictHint.trim().isEmpty())) {
                hint = "You haven't set an API Key or the Key is invalid. Here is a hint from the dictionary: "
                        + hintDataAccessInterface.getDictHint(word);
            }
        }
        if (hint == null || hint.trim().isEmpty()) {
            hint = "No hint available.";
        }
        final HintOutputData hintOutputData = new HintOutputData(hint);
        System.out.println("----------------------------");
        System.out.println("The Hint is :" + hint);
        System.out.println("----------------------------");
        hintOutputBoundary.prepareSuccessView(hintOutputData);
    }
}
