package use_case.generate_hint;

public interface InMemoryHintDataAccessInterface {

    /**
     * It will decrease the hint attempt in DAO (in entity).
     */
    void decreaseHintAttempt();

    /**
     * It will get the hint attempt in DAO (in entity).
     * @return the hint attempt in DAO
     */
    int getHintAttempts();

    /**
     * It will get the current word that player guess in DAO (in entity).
     * @return the current word in DAO
     */
    String getCurrentWord();

}
