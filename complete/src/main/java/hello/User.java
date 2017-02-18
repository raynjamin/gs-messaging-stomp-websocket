package hello;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Ben on 2/16/17.
 */
public class User {
    @JsonIgnore
    private String sessionId;
    private String name;
    private Room room;

    public User() {
    }

    public User(String sessionId, String name) {
        this.name = name;
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
