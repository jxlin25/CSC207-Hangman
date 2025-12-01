package use_case.generate_hint;

import use_case.make_guess.HangmanGameDataAccessInterface;

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
        final String word = new String(hangmanGameDataAccessInterface.getCurrentWordPuzzle().getLetters());
        int remainHint = hangmanGameDataAccessInterface.getHangmanGame().getHintAttempts();
        String hint = "";

        if (remainHint <= 0) {
            hint = "Don't have hint attempts";
        }
        else {
            if (hintDataAccessInterface.isApiKeyValid()) {
                hint = hintDataAccessInterface.getGemiHint(word);
            }
            else {
                final String dictHint = hintDataAccessInterface.getDictHint(word);
                if (!(dictHint == null || dictHint.trim().isEmpty())) {
                    hint = "You haven't set an API Key or the Key is invalid. Here is a hint from the dictionary: "
                            + hintDataAccessInterface.getDictHint(word);
                }
            }
            if (hint == null || hint.trim().isEmpty()) {
                hint = "No hint available.";
            }
            hangmanGameDataAccessInterface.getHangmanGame().decreasingHint();
            remainHint = hangmanGameDataAccessInterface.getHangmanGame().getHintAttempts();

        }
        final HintOutputData hintOutputData = new HintOutputData(hint, remainHint);
        System.out.println("----------------------------");
        System.out.println("The Hint is :" + hint);
        System.out.println("----------------------------");
        hintOutputBoundary.prepareSuccessView(hintOutputData);
    }
}
