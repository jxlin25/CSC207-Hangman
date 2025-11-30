package data_access;

import entity.Guess;
import entity.HangmanGame;
import entity.Round;
import entity.WordPuzzle;
import use_case.choose_difficulty.ChooseDifficultyDataAccessInterface;
import use_case.initialize_round.InitializeRoundDataAccessInterface;
import use_case.make_guess.HangmanGameDataAccessInterface;

public class InMemoryHangmanDataAccessObject implements
        HangmanGameDataAccessInterface,
        InitializeRoundDataAccessInterface,
        ChooseDifficultyDataAccessInterface {

    private HangmanGame currentHangmanGame;

    public InMemoryHangmanDataAccessObject() {

    }

    @Override
    public HangmanGame getHangmanGame() {
        return this.currentHangmanGame;
    }

    @Override
    public Round getCurrentRound() {
        return this.getHangmanGame().getCurrentRound();
    }

    @Override
    public void setHangmanGame(HangmanGame hangmanGame) {
        this.currentHangmanGame = hangmanGame;
    }

    @Override
    public void decreaseCurrentRoundAttempt() {
        this.getCurrentRound().setAttempt(this.getCurrentRound().getAttempt() - 1);
    }

    @Override
    public void addGuessToCurrentRound(Guess guess) {
        this.getCurrentRound().addGuess(guess);
    }

    // Set current round as WON and move on to the next round
    @Override
    public boolean setCurrentRoundWonAndStartNextRound() {
        return this.getHangmanGame().startNextRound(true);
    }

    // Set current round as LOST and move on to the next round
    @Override
    public boolean setCurrentRoundLostAndStartNextRound() {
        return this.getHangmanGame().startNextRound(false);
    }

    @Override
    public int getTotalRoundNumber() {
        return this.getHangmanGame().getTotalRounds();
    }

    @Override
    public int getCurrentRoundAttempt() {
        return this.getCurrentRound().getAttempt();
    }

    @Override
    public WordPuzzle getCurrentWordPuzzle() {
        return this.getCurrentRound().getWordPuzzle();
    }

    @Override
    public boolean isGuessCorrectToCurrentWordPuzzle(Guess guess) {
        // If the letter in the guess corresponds to a hidden letter in the puzzle, this is a correct guess
        return this.getCurrentWordPuzzle().isLetterHidden(guess.getLetter());
    }

    @Override
    // Reveal the given letter in the word puzzle
    public void revealLetterInCurrentWordPuzzle(Guess guess) {
        this.getCurrentWordPuzzle().revealLetter(guess.getLetter());
    }

    @Override
    // Check if the puzzle is completed
    public boolean isCurrentWordPuzzleComplete() {
        return this.getCurrentWordPuzzle().isPuzzleComplete();
    }

    public boolean isCurrentRoundTheLastRound() {
        return this.getHangmanGame().getCurrentRoundIndex() == this.getHangmanGame().getRounds().size() - 1;
    }

    @Override
    public boolean[] getCurrentWordPuzzleRevealedLettersBooleans() {
        return this.getCurrentWordPuzzle().getRevealedLettersBooleans();
    }

    @Override
    public String getCurrentMaskedWord() {
        return this.getCurrentWordPuzzle().getMaskedWord();
    }

    @Override
    public char[] getCurrentWordPuzzleLetters() {
        return this.getCurrentWordPuzzle().getLetters();
    }

    @Override
    public int getCurrentRoundNumber() {
        return this.getHangmanGame().getCurrentRoundNumber();
    }

    @Override
    public String getCurrentWord() {
        char[] letters = this.getCurrentWordPuzzleLetters();
        return new String(letters);
    }

    @Override
    public void setMaxAttempt(int maxAttempt) {
        this.getHangmanGame().setMaxAttempts(maxAttempt);

        for (Round round : this.getHangmanGame().getRounds()) {
            round.setAttempt(maxAttempt);
        }
    }

    @Override
    public void setHintAttempts(int hintAttempts) {
        this.getHangmanGame().setHintAttempts(hintAttempts);
    }

    @Override
    public int getHintAttempts() {
        return this.getHangmanGame().getHintAttempts();
    }

    public int getMaxAttempts() {
        return this.getHangmanGame().getMaxAttempts();
    }

//    @Override
//    public int getInitialAttemptsForGame() {
//        // Example logic: if  Round entity keeps the starting attempts of round 1,
//        // return that. Adjust to actual model.
//        return this.getHangmanGame().getMaxAttempts();
}

