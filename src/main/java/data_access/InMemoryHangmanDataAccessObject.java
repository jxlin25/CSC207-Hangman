package data_access;

import entity.Guess;
import entity.HangmanGame;
import entity.Round;
import entity.WordPuzzle;
import use_case.InitializeFirstRound.InitializeFirstRoundDataAccessInterface;
import use_case.MakeGuess.MakeGuessHangmanGameDataAccessInterface;

public class InMemoryHangmanDataAccessObject implements MakeGuessHangmanGameDataAccessInterface, InitializeFirstRoundDataAccessInterface {

    private HangmanGame currentHangmanGame;

    public InMemoryHangmanDataAccessObject() {}

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
    public void addGuessToCurrentRound(Guess guess){
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
    public int getCurrentRoundAttempt() {
        return  this.getCurrentRound().getAttempt();
    }

    @Override
    public WordPuzzle getCurrentWordPuzzle() {
        return this.getCurrentRound().getWordPuzzle();
    }

    @Override
    public boolean isGuessCorrect(Guess guess){
        // If the letter in the guess corresponds to a hidden letter in the puzzle, this is a correct guess
        return this.getCurrentWordPuzzle().isLetterHidden(guess.getLetter());
    }

    @Override
    // Reveal the given letter in the word puzzle
    public void revealLetter(Guess guess) {
        this.getCurrentWordPuzzle().revealLetter(guess.getLetter());
    }

    @Override
    // Check if the puzzle is completed
    public boolean isPuzzleComplete() {
        return this.getCurrentWordPuzzle().isPuzzleComplete();
    }

    @Override
    public char[] getWordPuzzle() {
        return this.getCurrentWordPuzzle().getLetters();
    }

    @Override
    public boolean[] getCurrentWordPuzzleRevealedLettersBooleans() {
        return this.getCurrentWordPuzzle().getRevealedLettersBooleans();
    }

    @Override
    public String getMaskedWord(){
        return this.getCurrentWordPuzzle().getMaskedWord();
    }

    @Override
    public int getCurrentRoundNumber() {
        return this.getHangmanGame().getCurrentRoundNumber();
    }

    // InitializeFirstRoundDataAccessInterface
    @Override
    public Round getFirstRound() {
        return this.currentHangmanGame.getRound(0);
    }

    @Override
    public String getFirstMaskedWord() {
        return this.getFirstWordPuzzle().getMaskedWord();
    }

    @Override
    public WordPuzzle getFirstWordPuzzle() {
        return this.getFirstRound().getWordPuzzle();
    }

    @Override
    public int getFirstRoundNumber() {
        return 1;
    }

    @Override
    public int getFirstRoundAttempt() {
        return this.getFirstRound().getAttempt();
    }
}

