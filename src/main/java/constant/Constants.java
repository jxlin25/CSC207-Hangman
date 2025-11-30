package constant;

public class Constants {
    public static final int NUMBER_OF_ENGLISH_ALPHABET = 26;
    public static final String RANDOM_WORD_API_URL =
            "https://random-word-api.vercel.app/api?length=";
    public static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";
    public static final String DICTIONARY_API_URL =
            "https://api.dictionaryapi.dev/api/v2/entries/en/";

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String API_KEY_ENV_NAME = "GEMINI_API_KEY";
    public static final int SUCCEED_CODE = 200;
    public static final String DEF_STRING = "Definition: ";

    public static final int HANGMAN_FIGURE_MAX_INDEX = 6;
}
