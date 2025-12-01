package use_case.generate_hint;

public interface InMemoryHintDataAccessInterface {

    void decreaseHintAttempt();

    int getHintAttempts();

    String getCurrentWord();

}
