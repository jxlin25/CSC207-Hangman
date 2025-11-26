package data_access;

import okhttp3.OkHttpClient;
import use_case.Hint.HintDataAccessInterface;

public class DBHintDataAccessObject implements HintDataAccessInterface {
    // I'm not sure should we use Dic Api or Ai api
    private static final String HINT_API_URL =
            "https://api.dictionaryapi.dev/api/v2/entries/en/";
    private final OkHttpClient client;

    private DBHintDataAccessObject() {
        this.client = new OkHttpClient().newBuilder().build();
    }


    @Override
    public String getHint(String word) {
        return "";
    }
}
