package data_access;

import okhttp3.*;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.Hint.HintDataAccessInterface;

import java.io.IOException;

public class DBHintDataAccessObject implements HintDataAccessInterface {
    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";
    private static final String DICTIONARY_API_URL =
            "https://api.dictionaryapi.dev/api/v2/entries/en/";

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String API_KEY_ENV_NAME = "GEMINI_API_KEY";

    private final OkHttpClient client;

    Boolean apiValid;

    public DBHintDataAccessObject() {
        this.client = new OkHttpClient().newBuilder().build();
    }

    private String getApiKey() {
        return System.getenv(API_KEY_ENV_NAME);
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
        JSONObject resp = new JSONObject()
                .put("contents", new JSONArray()
                        .put(new JSONObject()
                                .put("parts", new JSONArray()
                                        .put(new JSONObject().put("text", "Hello"))
                                )
                        ));

        Request request = new Request.Builder()
                .url(GEMINI_API_URL + getApiKey())
                .post(RequestBody.create(resp.toString(), MediaType.parse(APPLICATION_JSON)))
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();

        try {
            Response response = client.newCall(request).execute();
            apiValid = (response.code() == 200);
            return apiValid;
        } catch (IOException e) {
            apiValid = false;
            return apiValid;
        }
    }

    @Override
    public String getGemiHint(String word) {
        final String prompt = "You are a Hangman game hint generator. " + "Please generate a hint about '" + word + "' for me, "
                + "The hint should not contain the word, but try to be as appropriate as possible. And Don't give me Option, just one hint.";
        JSONObject response = new JSONObject()
                .put("contents", new JSONArray()
                        .put(new JSONObject()
                                .put("parts", new JSONArray()
                                        .put(new JSONObject().put("text", prompt))
                                )
                        ));
        Request request = new Request.Builder()
                .url(GEMINI_API_URL + getApiKey())
                .post(RequestBody.create(response.toString(), MediaType.parse(APPLICATION_JSON)))
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();

        try {
            Response resp = client.newCall(request).execute();
            if (resp.code() != 200) {
                return "AI hint unavailable.";
            }

            JSONObject body = new JSONObject(resp.body().string());
            return body
                    .getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text").trim();

        } catch (IOException e) {
            return "We can't get Ai hint.";
        }
    }

    @Override
    public String getDictHint(String word) {
        Request request = new Request.Builder()
                .url(DICTIONARY_API_URL + word)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() != 200) {
                return "No dictionary hint available.";
            }

            JSONArray body = new JSONArray(response.body().string());
            JSONObject firstEntry = body.getJSONObject(0);

            JSONArray meanings = firstEntry.getJSONArray("meanings");

            // a word may have many part of speech, I hope first return the def of noun
            String definitions1 = getNounDef(meanings);
            if (definitions1 != null) {
                return definitions1;
            }

            // if don't have noun, return the first def
            JSONObject firstMeaning = meanings.getJSONObject(0);
            JSONArray definitions = firstMeaning.getJSONArray("definitions");
            return "Definition: " + definitions.getJSONObject(0).getString("definition");

        } catch (Exception e) {
            return "We can't get Dictionary hint.";
        }
    }

    // just try
    @Nullable
    private static String getNounDef(JSONArray meanings) {
        for (int i = 0; i < meanings.length(); i++) {
            JSONObject meaning = meanings.getJSONObject(i);
            if ("noun".equalsIgnoreCase(meaning.optString("partOfSpeech"))) {
                JSONArray definitions = meaning.getJSONArray("definitions");
                if (!definitions.isEmpty()) {
                    return definitions.getJSONObject(0).getString("definition");
                }
            }
        }
        return null;
    }
}
