package interface_adapter.SubmitWord;

import use_case.SubmitWord.SubmitWordOutputBoundary;
import use_case.SubmitWord.SubmitWordOutputData;

public class SubmitWordPresenter implements SubmitWordOutputBoundary {
    private final SubmitWordViewModel viewModel;

    public SubmitWordPresenter(SubmitWordViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(SubmitWordOutputData outputData) {
        SubmitWordState state = viewModel.getState();
        state.setSuccessMessage(outputData.getMessage());
        state.setError(null);
        viewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        SubmitWordState state = viewModel.getState();
        state.setError(error);
        state.setSuccessMessage(null);
        viewModel.firePropertyChange();
    }
}
