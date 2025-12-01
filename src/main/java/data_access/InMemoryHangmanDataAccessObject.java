package data_access;

import entity.Guess;
import entity.HangmanGame;
import entity.Round;
import entity.WordPuzzle;
import use_case.choose_difficulty.ChooseDifficultyDataAccessInterface;
import use_case.endgame_results.EndGameResultsDataAccessInterface;
import use_case.generate_hint.InMemoryHintDataAccessInterface;
import use_case.initialize_round.InitializeRoundDataAccessInterface;
import use_case.make_guess.MakeGuessDataAccessInterface;

public class InMemoryHangmanDataAccessObject implements
        MakeGuessDataAccessInterface,
        InitializeRoundDataAccessInterface,
        InMemoryHintDataAccessInterface,
        EndGameResultsDataAccessInterface,
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
    public void saveCurrentRound(Round round) {
        final int index = currentHangmanGame.getCurrentRoundIndex();
        this.currentHangmanGame.getRounds().set(index, round);
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

    @Override
    public void startNextRound() {
        this.getHangmanGame().startNextRound();
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

    public boolean isCurrentRoundTheLastRound() {
        return this.getHangmanGame().getCurrentRoundIndex() == this.getHangmanGame().getRounds().size() - 1;
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
    public void decreaseHintAttempt() {
        this.getHangmanGame().decreaseHintAttempt();
    }

    @Override
    public int getHintAttempts() {
        return this.getHangmanGame().getHintAttempts();
    }

    public int getMaxAttempts() {
        return this.getHangmanGame().getMaxAttempts();
    }
}

