package data_access;

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
    public String getHint(String word) {
        return "nothing";
    }

}