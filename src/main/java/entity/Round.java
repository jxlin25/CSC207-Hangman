package entity;

import java.util.ArrayList;

public class Round {
    //leave for multiplayer
    //private String roundId;
    private WordPuzzle word;
    private String Status;
    private ArrayList<Guess> guesses;

    public Round(WordPuzzle word) {
        this.word = word;
    }

    public boolean isGuessCorrect(Guess guess) {
        return this.word.isLetterHidden(guess.getLetter());
    }

    public boolean isPuzzleComplete(){
        return word.isPuzzleComplete();
    }
}
