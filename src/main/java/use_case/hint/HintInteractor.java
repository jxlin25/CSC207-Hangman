package use_case.hint;

import entity.Hint;

public class HintInteractor implements HintInputBoundary {

    private final HintDataAccessInterface dao;
    private final HintOutputBoundary presenter;

    public HintInteractor(HintDataAccessInterface dao,
                          HintOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }

    @Override
    public void execute(HintInputData inputData) {
        String word = inputData.getWord();
        String hintText = dao.fetchHint(word);

        Hint hint = new Hint(hintText);
        HintOutputData output = new HintOutputData(hint);

        presenter.present(output);
    }
}
