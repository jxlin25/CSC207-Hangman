package entity;

public class WordPuzzle {

    private char[] letters;
    private boolean[] revealedLetters; // the indexes of the revealed letter in letters; eg. letters = ['A', 'p', 'p', 'l', 'e'], to have letter 'A' and 'l' revealed, revealedLetterIndexes = [true, false, false, true, false]

    // Constructor of WordPuzzle with no letters revealed
    public WordPuzzle(char[] letters) {
        this.letters = letters;
        this.revealedLetters = new boolean[letters.length];
        for( int i = 0; i < letters.length; i++ ){
            this.revealedLetters[i] = false;
        }
    }

    public char[] getLetters() {
        return letters;
    }

    public void setLetters(char[] letters) {
        this.letters = letters;
    }

    //this getter can be used to modify the revealedLetters boolean array due to array's mutability
    public boolean[] getRevealedLetters() {
        return revealedLetters;
    }

    public boolean isLetterInWord(char letter, boolean revealLetter) {
        for( int i = 0; i < letters.length; i++ ){
            if (letters[i] == letter){

                if (revealLetter == true){
                    this.revealedLetters[i] = true;
                }

                return true;
            }
        }
        return false;
    }

    public boolean isPuzzleComplete(){
        for ( int i = 0; i < revealedLetters.length; i++ ){
            if (revealedLetters[i] == false){
                return false;
            }
        }

        return true;
    }

}
