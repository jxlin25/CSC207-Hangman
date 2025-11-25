package entity;
import java.util.UUID;

public class Player {

    private String name;
    //leave for multiplayer mode
    private final String id;
    private Integer roomId;
    private int score;
    private String status;
    private long timeTaken;
    private int attempt;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.timeTaken = 0;
        this.status = "Waiting";
        this.id = UUID.randomUUID().toString();
        
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

    public void setRoomId(int roomId) {
        if (this.roomId == null) {
            this.roomId = roomId;
        }
    }

    public void addToScore(int points) {
        this.score += points;
    }

    public int getRoomId() {return roomId;}
    public String getId() {return id;}
}
