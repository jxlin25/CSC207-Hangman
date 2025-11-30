package interface_adapter.SubmitWord;

public class SubmitWordState {
    private String word = "";
    private String error = null;
    private String successMessage = null;
    private String roomId = null; // Added roomId field

    public SubmitWordState() {
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
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getRoomId() { // Added getter for roomId
        return roomId;
    }

    public void setRoomId(String roomId) { // Added setter for roomId
        this.roomId = roomId;
    }
}
