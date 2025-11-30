package interface_adapter.GameSession;
public class SubmitWordState {
    private String roomId = "";
    private String word = "";
    private String error = null;
    private String successMessage = null;
    private boolean isSubmitting = false;
    private boolean isWordSubmitted = false;
    private String currentPlayerId = "";
    private boolean isWordSetter = false;

    // Copy constructor
    public SubmitWordState(SubmitWordState copy) {
        this.roomId = copy.roomId;
        this.word = copy.word;
        this.error = copy.error;
        this.successMessage = copy.successMessage;
        this.isSubmitting = copy.isSubmitting;
        this.isWordSubmitted = copy.isWordSubmitted;
        this.currentPlayerId = copy.currentPlayerId;
        this.isWordSetter = copy.isWordSetter;
    }


    // Default constructor
    public SubmitWordState() {}

    // Getters and Setters
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
        // Clear success message when error occurs
        if (error != null) {
            this.successMessage = null;
        }
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
        // Clear error when success occurs
        if (successMessage != null) {
            this.error = null;
        }
    }

    public boolean isSubmitting() {
        return isSubmitting;
    }

    public void setSubmitting(boolean submitting) {
        isSubmitting = submitting;
    }

    public boolean isWordSubmitted() {
        return isWordSubmitted;
    }

    public void setWordSubmitted(boolean wordSubmitted) {
        isWordSubmitted = wordSubmitted;
    }

    public String getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(String currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public boolean isWordSetter() {
        return isWordSetter;
    }

    public void setWordSetter(boolean wordSetter) {
        isWordSetter = wordSetter;
    }

    /**
     * Resets the state to initial values, preserving room and player info
     */
    public void resetSubmissionState() {
        this.word = "";
        this.error = null;
        this.successMessage = null;
        this.isSubmitting = false;
        // Don't reset isWordSubmitted - that should persist
    }

    /**
     * Clears all messages (error and success)
     */
    public void clearMessages() {
        this.error = null;
        this.successMessage = null;
    }

    /**
     * Checks if the current word is valid for submission
     */
    public boolean isWordValid() {
        return word != null &&
                word.length() >= 3 &&
                word.matches("[a-zA-Z]+") &&
                !isSubmitting &&
                !isWordSubmitted;
    }

    @Override
    public String toString() {
        return "SubmitWordState{" +
                "roomId='" + roomId + '\'' +
                ", word='" + word + '\'' +
                ", error='" + error + '\'' +
                ", successMessage='" + successMessage + '\'' +
                ", isSubmitting=" + isSubmitting +
                ", isWordSubmitted=" + isWordSubmitted +
                ", currentPlayerId='" + currentPlayerId + '\'' +
                ", isWordSetter=" + isWordSetter +
                '}';
    }
}