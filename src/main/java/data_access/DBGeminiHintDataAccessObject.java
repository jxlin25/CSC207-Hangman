package data_access;

import use_case.hint.HintDataAccessInterface;

public class DBGeminiHintDataAccessObject implements HintDataAccessInterface {

    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final int SUCCESS_CODE = 200;

    public DBGeminiHintDataAccessObject() {}

    @Override
    public String getHint(String word) {
        return "nothing";
    }
}