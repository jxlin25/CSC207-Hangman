package interface_adapter.MakeGuess;

import entity.Guess;

public class MakeGuessState {
    /**
     * This class is the State of MarkGuess is stored. I think it might contain
     * information such as which letter the player guessed, where it was
     * located, and whether the guess was correct.
     *
     * e.g.
     *  guess.getletter -> "a"
     *  located - > 3  (the third letter is a)
     *  istrue() - > true - > it true letter
     *
     *  then view- >  ------   to  --a---
     */
    private Guess guess;

    public Guess getGuess() {
        return guess;
    }

    public void setWordPuzzle(Guess guess) {
        this.guess = guess;
    }
    //TODO
}
