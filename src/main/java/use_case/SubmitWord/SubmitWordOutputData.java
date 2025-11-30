package use_case.SubmitWord;

public class SubmitWordOutputData {
    private final boolean success;
    private final String message;

    public SubmitWordOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
