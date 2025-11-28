package data_access;

import okhttp3.*;
import org.json.JSONArray;
import use_case.GenerateWord.GenerateWordDataAccessInterface;

import java.io.IOException;
import java.util.Random;

public class DBGenerateWordDataAccessObject implements GenerateWordDataAccessInterface {

    private static final String RANDOM_WORD_API_URL =
            "https://random-word-api.vercel.app/api?length=";

    private static final String DICTIONARY_API_URL =
            "https://api.dictionaryapi.dev/api/v2/entries/en/";

    private final OkHttpClient client;

    private final Random random = new Random();

    private String word;

    public DBGenerateWordDataAccessObject() {
        this.client = new OkHttpClient().newBuilder().build();
    }

    @Override
    public String getRandomWord() {
        final int minLength = 3;
        final int maxLength = 6;
        final int length = random.nextInt(maxLength - minLength + 1) + minLength;
        final Request request = new Request.Builder()
                .url(RANDOM_WORD_API_URL + length)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONArray responseBody = new JSONArray(response.body().string());
            final String randomWord = responseBody.getString(0);
            return randomWord;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isValidWord(String word) {
        final Request request = new Request.Builder()
                .url(DICTIONARY_API_URL + word)
                .build();

        try {
            Response response = client.newCall(request).execute();
            final String responseBody = response.body().string();
            if (response.code() == 200 && responseBody.startsWith("[")) {
                return true;
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
