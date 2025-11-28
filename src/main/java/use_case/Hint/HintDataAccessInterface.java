package use_case.Hint;

/**
 * The DAO interface for the Hint Use Case.
 */
public interface HintDataAccessInterface {
    /**
     * Determine whether the player has a Gemini API Key. If not, return False; otherwise, return True.
     * @return boolean.
     */

    boolean isApiKeyValid();
    /**
     * Ask the prompt for the input word through Gemini 2.5 Api and return.
     * @param word the word we want to get prompt.
     * @return the prompt of input word.
     */

    String getGemiHint(String word);
    /**
     * Ask the prompt for the input word through Dictionary Api and return.
     * @param word the word we want to get prompt
     * @return the prompt of input word.
     */

    String getDictHint(String word);
}
