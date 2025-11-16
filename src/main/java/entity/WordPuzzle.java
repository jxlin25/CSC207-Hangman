package entity;

public class WordPuzzle {

    private char[] letters;

    /*
     * The indexes of the revealed letters in `letters`.
     * Example:
     * letters = ['A', 'p', 'p', 'l', 'e']
     * To have letter 'A' and 'l' revealed:
     * revealedLettersBooleans = [true, false, false, true, false]
     */
    private boolean[] revealedLettersBooleans;

    // Constructor of WordPuzzle with no letters revealed
    public WordPuzzle(char[] letters) {
        this.letters = letters;
        this.revealedLettersBooleans = new boolean[letters.length];
        for (int i = 0; i < letters.length; i++) {
            this.revealedLettersBooleans[i] = false;
        }
    }

    public char[] getLetters() {
        return this.letters.clone();
    }

    public boolean[] getRevealedLettersBooleans() {
        return revealedLettersBooleans.clone();
    }

    public char[] getRevealedLetters() {

        // Count how many letters have been revealed
        int revealedLettersCount = 0;
        for (boolean a : this.revealedLettersBooleans) {
            if (a == true) {
                revealedLettersCount++;
            }
        }

        char[] revealedLetters = new char[revealedLettersCount];
        int index = 0;

        for (int i = 0; i < revealedLettersBooleans.length; i++) {
            if (revealedLettersBooleans[i] == true) {
                revealedLetters[index] = letters[i];
                index++;
            }
        }

        return revealedLetters;
    }

    public char[] getHiddenLetters() {

        // Count how many letters have not been revealed
        int hiddenLettersCount = 0;
        for (boolean a : this.revealedLettersBooleans) {
            if (a == false) {
                hiddenLettersCount++;
            }
        }

        char[] hiddenLetters = new char[hiddenLettersCount];
        int index = 0;

        for (int i = 0; i < revealedLettersBooleans.length; i++) {
            if (this.revealedLettersBooleans[i] == false) {
                hiddenLetters[index] = letters[i];
                index++;
            }
        }

        return hiddenLetters;
    }

    // Check if all letters have been revealed
    public boolean isPuzzleComplete() {
        for (boolean a : this.revealedLettersBooleans) {
            if (a == false) {
                return false;
            }
        }
        return true;
    }

    // Reveal all occurrences of a given letter
    public void revealLetter(char letter) {
        for (int i = 0; i < letters.length; i++) {
            if (letters[i] == letter) {
                this.revealedLettersBooleans[i] = true;
            }
        }
    }

    // Check if a given letter appears in the word
    public boolean isLetterInWord(char letter) {
        for (int i = 0; i < letters.length; i++) {
            if (letters[i] == letter) {
                return true;
            }
        }
        return false;
    }

    // Check if a given letter has been revealed
    public boolean isLetterRevealed(char letter) {
        for (int i = 0; i < letters.length; i++) {
            if (letters[i] == letter) {
                if (revealedLettersBooleans[i]) {
                    return true;
                }
            }
        }
        return false;
    }
}
