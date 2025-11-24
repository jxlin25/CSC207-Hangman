package entity;

/**
 * Represents a single word puzzle in a round of Hangman game, storing the target word.
 */
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

//    /**
//     * Gets the revealed letters in the WordPuzzle
//     * @return array of chars that have been revealed in the WordPuzzle
//     */
//    public char[] getRevealedLetters() {
//
//        // Count how many letters have been revealed
//        int revealedLettersCount = 0;
//        for (boolean a : this.revealedLettersBooleans) {
//            if (a) {
//                revealedLettersCount++;
//            }
//        }
//
//        final char[] revealedLetters = new char[revealedLettersCount];
//        int index = 0;
//
//        for (int i = 0; i < revealedLettersBooleans.length; i++) {
//            if (revealedLettersBooleans[i]) {
//                revealedLetters[index] = letters[i];
//                index++;
//            }
//        }
//
//        return revealedLetters;
//    }
//
//    public char[] getHiddenLetters() {
//
//        // Count how many letters have not been revealed
//        int hiddenLettersCount = 0;
//        for (boolean a : this.revealedLettersBooleans) {
//            if (!a) {
//                hiddenLettersCount++;
//            }
//        }
//
//        char[] hiddenLetters = new char[hiddenLettersCount];
//        int index = 0;
//
//        for (int i = 0; i < revealedLettersBooleans.length; i++) {
//            if (!this.revealedLettersBooleans[i]) {
//                hiddenLetters[index] = letters[i];
//                index++;
//            }
//        }
//
//        return hiddenLetters;
//    }

    /**
     * Checks if the puzzle is completed by check if all the letter has been revealed in the WordPuzzle.
     * @return boolean of whether all the letters have been marked as revealed,
     * which is an indication of whether the puzzle is completed or not
     */    public boolean isPuzzleComplete() {
        for (boolean a : this.revealedLettersBooleans) {
            if (!a) {
                return false;
            }
        }
        return true;
    }

    /**
     * Mark all the occurrences of the letter in the word as revealed.
     * @param letter A letter that is going to be assessed
     */    public void revealLetter(char letter) {
        for (int i = 0; i < letters.length; i++) {
            if (Character.toLowerCase(letters[i]) == letter) {
                this.revealedLettersBooleans[i] = true;
            }
        }
    }

    /**
     * Checks if the letter is in the WordPuzzle.
     * @param letter A letter that is going to be assessed
     * @return boolean of whether the letter exists in the word
     */    public boolean isLetterInWord(char letter) {
        for (char c : letters) {
            if (Character.toLowerCase(c) == letter) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the letter is revealed in the WordPuzzle.
     * @param letter A letter that is going to be assessed
     * @return boolean of whether the letter is revealed
     */
    public boolean isLetterRevealed(char letter) {
        for (int i = 0; i < letters.length; i++) {
            if (Character.toLowerCase(letters[i]) == letter) {
                if (revealedLettersBooleans[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the letter is not revealed in the WordPuzzle.
     * @param letter A letter that is going to be assessed
     * @return boolean of whether the letter is not revealed
     */
    public boolean isLetterHidden(char letter) {
        for (int i = 0; i < letters.length; i++) {
            if (Character.toLowerCase(letters[i]) == letter) {
                if (!revealedLettersBooleans[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the masked string of the word.
     * @return String of the masked word with unrevealed character being replaced by '_'
     */
    public String getMaskedWord() {
        StringBuilder masked = new StringBuilder();

        for (int i = 0; i < this.letters.length; i++) {
            char currentLetter = this.letters[i];
            boolean isRevealed = this.revealedLettersBooleans[i];

            if (isRevealed) {
                masked.append(currentLetter);
            } else {
                masked.append("_");
            }

            masked.append(" ");
        }

        return masked.toString().trim().toUpperCase();
    }
}
