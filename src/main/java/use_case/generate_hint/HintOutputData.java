package use_case.generate_hint;

/**
 * Output Data for the Generate Word Use Case.
 */

public class HintOutputData {
    private String hint;
    private int remainHintAttempts;

    public HintOutputData(String hint, int remainHintAttempts) {
        this.hint = hint;
        this.remainHintAttempts = remainHintAttempts;
    }

    public String getHint() {
        return hint;
    }

    public int getRemainHintAttempts() {
        return remainHintAttempts;
    }
}
