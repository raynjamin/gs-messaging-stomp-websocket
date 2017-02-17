package hello;

/**
 * Created by Ben on 2/16/17.
 */
public class User {
    private String sessionId;
    private Room room;

    public User() {
    }

    public User(String sessionId) {
        this.sessionId = sessionId;
        this.room = GreetingController.startRoom;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
