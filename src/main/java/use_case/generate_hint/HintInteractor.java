package use_case.generate_hint;

/**
 * The Generate Word Interactor.
 */

public class HintInteractor implements HintInputBoundary {
    private final DatabaseHintDataAccessInterface databaseHintDataAccessObject;
    private final HintOutputBoundary hintOutputBoundary;
    private final InMemoryHintDataAccessInterface inMemoryHintDataAccessObject;

    public HintInteractor(DatabaseHintDataAccessInterface databaseHintDataAccessObject,
                          HintOutputBoundary hintOutputBoundary,
                          InMemoryHintDataAccessInterface inMemoryHintDataAccessObject) {
        this.databaseHintDataAccessObject = databaseHintDataAccessObject;
        this.hintOutputBoundary = hintOutputBoundary;
        this.inMemoryHintDataAccessObject = inMemoryHintDataAccessObject;
    }

    @Override
    public void execute() {
        final String word = inMemoryHintDataAccessObject.getCurrentWord();
        final int remainHint = inMemoryHintDataAccessObject.getHintAttempts();
        String hint = null;
        if (remainHint <= 0) {
            hint = "Don't have hint attempts";
        }
        else {
            inMemoryHintDataAccessObject.decreaseHintAttempt();
            if (databaseHintDataAccessObject.isApiKeyValid()) {
                hint = databaseHintDataAccessObject.getGemiHint(word);
            }
            else {
                final String dictHint = databaseHintDataAccessObject.getDictHint(word);
                if (!(dictHint == null || dictHint.trim().isEmpty())) {
                    hint = "You haven't set an API Key or the Key is invalid. Here is a hint from the dictionary: "
                            + databaseHintDataAccessObject.getDictHint(word);
                }
            }
        }
        if (hint == null || hint.trim().isEmpty()) {
            hint = "No hint available.";
        }
        final int nowHint = inMemoryHintDataAccessObject.getHintAttempts();
        final HintOutputData hintOutputData = new HintOutputData(hint, nowHint);
        System.out.println("----------------------------");
        System.out.println("The Hint is :" + hint);
        System.out.println("----------------------------");
        hintOutputBoundary.prepareSuccessView(hintOutputData);
    }
}
