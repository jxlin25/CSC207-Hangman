package data_access;

import java.io.IOException;
import java.util.Random;

import org.json.JSONArray;

import Constant.Constants;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import use_case.GenerateWord.GenerateWordDataAccessInterface;

public class DBGenerateWordDataAccessObject implements GenerateWordDataAccessInterface {

    private final OkHttpClient client;

    private final Random random = new Random();

    public DBGenerateWordDataAccessObject() {
        this.client = new OkHttpClient().newBuilder().build();
    }

    @Override
    public String getRandomWord() {
        String result = null;
        final int minLength = 3;
        final int maxLength = 6;
        final int length = random.nextInt(maxLength - minLength + 1) + minLength;
        final Request request = new Request.Builder()
                .url(Constants.RANDOM_WORD_API_URL + length)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONArray responseBody = new JSONArray(response.body().string());
            final String randomWord = responseBody.getString(0);
            result = randomWord;
        }
        catch (IOException ioException) {
            result = null;
        }
        return result;
    }

    @Override
    public boolean isValidWord(String word) {
        final Request request = new Request.Builder()
                .url(Constants.DICTIONARY_API_URL + word)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final String responseBody = response.body().string();
            if (response.code() == Constants.SUCCEED_CODE && responseBody.startsWith("[")) {
                return true;
            }
            return false;
        }
        catch (IOException ioException) {
            return false;
        }
    }
}
