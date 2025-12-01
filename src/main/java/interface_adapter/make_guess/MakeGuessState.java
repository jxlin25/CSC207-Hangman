package interface_adapter.make_guess;


/** The State object for the MakeGuessViewModel.
 */
public class MakeGuessState {

    private String guessedLetter = "";
    private boolean isGuessCorrect;
    private String roundStatus = constant.StatusConstant.GUESSING;
    private boolean isGameOver;
    private int remainingAttempts;
    private int currentRoundNumber = 1;
    private String maskedWord = "";
    private String message = "";
    private String hintText;
    private boolean resetAlphabetButtons;
    private int maxAttempts = 6;
    private int totalRound = 1;
    private int hintAttempts;
    private String fullWord = "";

    public MakeGuessState() {

    }

    public String getGuessedLetter() {
        return guessedLetter;
    }

    public void setGuessedLetter(String guessedLetter) {
        this.guessedLetter = guessedLetter;
    }

    public boolean isGuessCorrect() {
        return isGuessCorrect;
    }

    public void setGuessCorrect(boolean guessCorrect) {
        this.isGuessCorrect = guessCorrect;
    }

    public String getRoundStatus() {
        return this.roundStatus;
    }

    public void setRoundStatus(String status) {
        this.roundStatus = status;
    }

    public String getMessage() {
        return message;
    }

    // Getters and Setters
    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.isGameOver = gameOver;
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    public void setRemainingAttempts(int remainingAttempts) {
        this.remainingAttempts = remainingAttempts;
    }

    public int getCurrentRoundNumber() {
        return currentRoundNumber;
    }

    public void setCurrentRoundNumber(int currentRoundNumber) {
        this.currentRoundNumber = currentRoundNumber;
    }

    public String getMaskedWord() {
        return maskedWord;
    }

    public void setMaskedWord(String maskedWord) {
        this.maskedWord = maskedWord;
    }

    public String getHintText() {
        return hintText;
    }

    public void setHintText(String hint) {
        this.hintText = hint;
    }

    public boolean getResetAlphabetButtons() {
        return resetAlphabetButtons;
    }

    public void setResetAlphabetButtons(boolean resetAlphabetButtons) {
        this.resetAlphabetButtons = resetAlphabetButtons;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public int getTotalRound() {
        return totalRound;
    }

    public void setTotalRound(int totalRound) {
        this.totalRound = totalRound;
    }

    public int getHintAttempts() {
        return hintAttempts;
    }

    public void setHintAttempts(int hintAttempts) {
        this.hintAttempts = hintAttempts;
    }

    public String getFullWord() {
        return fullWord;
    }

    public void setFullWord(String fullWord) {
        this.fullWord = fullWord;
    }
}
