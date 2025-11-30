package use_case.generate_hint;

/**
 * Output Data for the Generate Word Use Case.
 */

public class HintOutputData {
    private String hint;
    private int remainHint;

    public HintOutputData(String hint, int remainHint) {
        this.hint = hint;
        this.remainHint = remainHint;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getRemainHint() {
        return remainHint;
    }
}
