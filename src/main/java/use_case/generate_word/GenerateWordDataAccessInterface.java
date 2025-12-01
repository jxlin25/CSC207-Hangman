package use_case.generate_word;

/**
 * The DAO interface for the Generate Word Use Case.
 */

public interface GenerateWordDataAccessInterface {

    /**
     * This will return a random word by Random Word Api. Don't need input.
     * @return a random word by Random Word Api.
     */
    String getRandomWord();

    /**
     * This is tested the word is Valid by Dictionary Api (make sure this word in Dictionary).
     * @param word The random word from Random Word Api (or maybe the Player enter in multiplayer)
     * @return if valid, return true, otherwise return false
     */
    boolean isValidWord(String word);
}
