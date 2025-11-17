//package data_access;
//
//import entity.WordPuzzle;
//import entity.Guess;
//import use_case.MakeGuess.MakeGuessWordPuzzleDataAccessInterface;
//
//public class InMemoryWordPuzzleDataAccessObject implements MakeGuessWordPuzzleDataAccessInterface{
//
//    private WordPuzzle currentPuzzle;
//
//    public InMemoryWordPuzzleDataAccessObject(WordPuzzle initialPuzzle) {
//        this.currentPuzzle = initialPuzzle;
//    }
//
//    @Override
//    public WordPuzzle getCurrentWordPuzzle() {
//        return currentPuzzle;
//    }
//
//    @Override
//    public boolean isGuessCorrect(Guess guess){
//        // If the letter in the guess corresponds to a hidden letter in the puzzle, this is a correct guess
//        return this.currentPuzzle.isLetterHidden(guess.getLetter());
//    }
//
//    @Override
//    // Reveal the given letter in the word puzzle
//    public void revealLetter(Guess guess) {
//        this.currentPuzzle.revealLetter(guess.getLetter());
//    }
//
//    @Override
//    // Check if the puzzle is completed
//    public boolean isPuzzleComplete() {
//        return this.currentPuzzle.isPuzzleComplete();
//    }
//
//}
