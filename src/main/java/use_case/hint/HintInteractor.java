package use_case.hint;

import entity.Hint;

public class HintInteractor implements HintInputBoundary {

    private final HintDataAccessInterface HintDataAccessObject;
    private final HintOutputBoundary HintPresenter;

    public HintInteractor(HintDataAccessInterface HintDataAccessObject,
                          HintOutputBoundary HintPresenter) {
        this.HintDataAccessObject = HintDataAccessObject;
        this.HintPresenter = HintPresenter;
    }

    @Override
    public void execute(HintInputData inputData) {
        String word = inputData.getWord();
        String hintText = HintDataAccessObject.getHint(word);

        Hint hint = new Hint(hintText);
        HintOutputData output = new HintOutputData(hint);

        HintPresenter.prepareSuccessView(output);
    }
}
