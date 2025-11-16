package use_case.Hint;

import entity.Hint;

public class HintOutputData {

    private final Hint hint;

    public HintOutputData(Hint hint) {
        this.hint = hint;
    }

    public Hint getHint() {
        return hint;
    }
}