package interface_adapter.GenerateWord;

import entity.WordPuzzle;

public class GenerateWordState {
    private WordPuzzle wordPuzzle = null;

    public WordPuzzle getWordPuzzle() {
        return wordPuzzle;
    }

    public void setWordPuzzle(WordPuzzle wordPuzzle) {
        this.wordPuzzle = wordPuzzle;
    }
}