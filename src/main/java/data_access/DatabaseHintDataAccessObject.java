package data_access;

import java.io.IOException;

import okhttp3.*;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import constant.Constants;
import use_case.generate_hint.DatabaseHintDataAccessInterface;


/**
 * If people want to use this DAO, they need valid Gemini 2.5 API KEY.
 * https://aistudio.google.com/api-keys
 * This is an API for accessing websites.
 * if people not have it and set it to Environment Variables, it will ues Dictionary API.
 */
public class DatabaseHintDataAccessObject implements DatabaseHintDataAccessInterface {

    private final OkHttpClient client;

    private Boolean apiValid;

    public DatabaseHintDataAccessObject() {
        this.client = new OkHttpClient().newBuilder().build();
    }

    private String getApiKey() {
        return System.getenv(Constants.API_KEY_ENV_NAME);
    }

    @Override
    public boolean isApiKeyValid() {
        if (null != apiValid) {
            return apiValid;
        }
        if (getApiKey() == null || getApiKey().isEmpty()) {
            apiValid = false;
            return apiValid;
        }
        final JSONObject resp = new JSONObject()
                .put("contents", new JSONArray()
                        .put(new JSONObject()
                                .put("parts", new JSONArray()
                                        .put(new JSONObject().put("text", "Hello"))
                                )
                        ));

        final Request request = new Request.Builder()
                .url(Constants.GEMINI_API_URL + getApiKey())
                .post(RequestBody.create(resp.toString(), MediaType.parse(Constants.APPLICATION_JSON)))
                .addHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            apiValid = response.code() == Constants.SUCCEED_CODE;
            return apiValid;
        }
        catch (IOException ioException) {
            apiValid = false;
            return apiValid;
        }
    }

    @Override
    public String getGemiHint(String word) {
        String result = "AI hint unavailable.";
        final String prompt = "You are a Hangman game hint generator. " + "Please generate a hint about '"
                + word + "' for me, "
                + "The hint should not contain the word, but try to be as appropriate as possible. "
                + "And Don't give me Option, just one hint.";
        final JSONObject response = new JSONObject()
                .put("contents", new JSONArray()
                        .put(new JSONObject()
                                .put("parts", new JSONArray()
                                        .put(new JSONObject().put("text", prompt))
                                )
                        ));
        final Request request = new Request.Builder()
                .url(Constants.GEMINI_API_URL + getApiKey())
                .post(RequestBody.create(response.toString(), MediaType.parse(Constants.APPLICATION_JSON)))
                .addHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .build();

        try {
            final Response resp = client.newCall(request).execute();
            if (resp.code() == Constants.SUCCEED_CODE) {
                final JSONObject body = new JSONObject(resp.body().string());
                final String actualHint = body
                        .getJSONArray("candidates")
                        .getJSONObject(0)
                        .getJSONObject("content")
                        .getJSONArray("parts")
                        .getJSONObject(0)
                        .getString("text").trim();
                if (actualHint != null && !actualHint.isEmpty()) {
                    result = actualHint;
                }
            }
        }
        catch (IOException ioException) {
            result = "We can't get Ai hint.";
        }
        return result;
    }

    @Override
    public String getDictHint(String word) {
        final Request request = new Request.Builder()
                .url(Constants.DICTIONARY_API_URL + word)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            if (response.code() != Constants.SUCCEED_CODE) {
                return "No dictionary hint available.";
            }

            final JSONArray body = new JSONArray(response.body().string());
            final JSONObject firstEntry = body.getJSONObject(0);

            final JSONArray meanings = firstEntry.getJSONArray("meanings");

            // Give priority to verb definitions
            final String verbHint = getPreferredHint(meanings, word, "verb");
            if (verbHint != null) {
                return Constants.DEF_STRING + verbHint;
            }

            // Select noun definitions
            final String nounHint = getPreferredHint(meanings, word, "noun");
            if (nounHint != null) {
                return Constants.DEF_STRING + nounHint;
            }

            // if don't have noun and vert.
            final String hint = getPreferredHint(meanings, word, "any");
            if (hint != null) {
                return Constants.DEF_STRING + hint;
            }

            return "No hint available.";

        }
        catch (IOException exception) {
            return "We can't get Dictionary hint.";
        }
    }

    // just try
    @Nullable
    private static String getPreferredHint(JSONArray meanings, String word, String partOfSpeech) {
        String result = null;
        String lowerWord = word.toLowerCase();
        for (int i = 0; i < meanings.length(); i++) {
            final JSONObject meaning = meanings.getJSONObject(i);

            if (!"any".equals(partOfSpeech) && !partOfSpeech.equalsIgnoreCase(meaning.optString("partOfSpeech"))) {
                continue;
            }

            final JSONArray definitions = meaning.getJSONArray("definitions");

            for (int j = 0; j < definitions.length(); j++) {
                final String def = definitions.getJSONObject(j).getString("definition");
                final String defLower = def.toLowerCase();

                if (defLower.contains(lowerWord)) {
                    continue;
                }
                result = def;
            }
        }
        return result;
    }
}
