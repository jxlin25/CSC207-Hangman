package use_case.Hint;

/**
 * The Generate Word Interactor.
 */

public class HintInteractor implements HintInputBoundary {
    private final HintDataAccessInterface hintDataAccessInterface;
    private final HintOutputBoundary hintOutputBoundary;

    public HintInteractor(HintDataAccessInterface hintDataAccessInterface,
                          HintOutputBoundary hintOutputBoundary) {
        this.hintDataAccessInterface = hintDataAccessInterface;
        this.hintOutputBoundary = hintOutputBoundary;
    }

    @Override
    public void execute(HintInputData hintInputData) {
        String hint;
        if (hintDataAccessInterface.isApiKeyValid()) {
            hint = hintDataAccessInterface.getGemiHint(hintInputData.getWord());
        }
        else {
            hint = "You don't set the Api key, this is definition from dictionary: "
                    + hintDataAccessInterface.getDictHint(hintInputData.getWord());
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
