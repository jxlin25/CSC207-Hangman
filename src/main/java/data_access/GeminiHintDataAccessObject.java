package data_access;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.hint.HintDataAccessInterface;

public class GeminiHintDataAccessObject implements HintDataAccessInterface {

    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final int SUCCESS_CODE = 200;
    private final String apiKey;

    public GeminiHintDataAccessObject(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String fetchHint(String word) {

        OkHttpClient client = new OkHttpClient();

        String url = "https://generativelanguage.googleapis.com/v1beta/models/"
                + "gemini-pro:generateContent?key=" + apiKey;

        JSONObject bodyJSON = new JSONObject();
        JSONArray contents = new JSONArray();
        JSONObject contentObj = new JSONObject();
        JSONArray parts = new JSONArray();
        JSONObject textObj = new JSONObject();

        textObj.put("text",
                "Give a short hint for the word: " + word + ". Do NOT reveal the word.");

        parts.put(textObj);
        contentObj.put("parts", parts);
        contents.put(contentObj);
        bodyJSON.put("contents", contents);

        RequestBody body = RequestBody.create(bodyJSON.toString(),
                MediaType.parse(CONTENT_TYPE_JSON));

        Request request = new Request.Builder()
                .url(url)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (response.code() != SUCCESS_CODE) {
                return "API returned error: " + response.code();
            }

            JSONObject responseJSON = new JSONObject(response.body().string());
            JSONArray candidates = responseJSON.getJSONArray("candidates");
            JSONObject firstCandidate = candidates.getJSONObject(0);
            JSONObject content = firstCandidate.getJSONObject("content");
            JSONArray resultParts = content.getJSONArray("parts");

            return resultParts.getJSONObject(0).getString("text");

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
