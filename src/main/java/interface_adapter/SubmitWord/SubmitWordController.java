package interface_adapter.SubmitWord;

import use_case.SubmitWord.SubmitWordInputBoundary;
import use_case.SubmitWord.SubmitWordInputData;

public class SubmitWordController {
    private final SubmitWordInputBoundary interactor;

    public SubmitWordController(SubmitWordInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String roomId, String word) {
        SubmitWordInputData inputData = new SubmitWordInputData(roomId, word);
        interactor.execute(inputData);
    }
}
