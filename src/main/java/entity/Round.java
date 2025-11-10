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
        //if the guessed letter is present in the word, update the revealedLetters and return true
        if(word.isLetterInWord(guess.getLetter(), true)){
            return true;
        }
        return false;
    }

    public boolean isPuzzleComplete(){
        return word.isPuzzleComplete();
    }
}
