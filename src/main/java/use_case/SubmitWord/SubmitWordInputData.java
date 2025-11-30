package use_case.SubmitWord;

public class SubmitWordInputData {
    private final String roomId;
    private final String word;

    public SubmitWordInputData(String roomId, String word) {
        this.roomId = roomId;
        this.word = word;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getWord() {
        return word;
    }
}
