package use_case.Hint;

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
        final String hint = hintDataAccessInterface.getHint(hintInputData.getWord());
        final HintOutputData hintOutputData = new HintOutputData(hint);
        System.out.println("----------------------------");
        System.out.println("The Hint is :" + hint);
        System.out.println("----------------------------");
        hintOutputBoundary.prepareSuccessView(hintOutputData);
    }
}
