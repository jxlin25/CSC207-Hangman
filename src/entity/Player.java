package entity;

public class Player {

    private String name;
    //leave for multiplayer mode
    //private String uid;
    private int score;
    private String status;
    private long timeTaken;
    private int attempt;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.timeTaken = 0;
        this.status = "Waiting";
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }
}
