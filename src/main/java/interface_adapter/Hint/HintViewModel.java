package interface_adapter.Hint;

import interface_adapter.ViewModel;

public class HintViewModel extends ViewModel {
    private String hintText = "";
    public String getHintText() { return hintText; }
    public void setHintText(String hintText) { this.hintText = hintText; }
}
