package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;

@Controller
public class GreetingController {
    public static HashMap<String, User> userSessions = new HashMap<>();
    public static Room startRoom;

    static {
        startRoom = new Room();
        Room w = new Room();
        w.setEast(startRoom);
        startRoom.setWest(w);
    }

    @Autowired
    SimpMessagingTemplate template;

    // /user/[sessionId]
    // /mud/[roomid]

    @MessageMapping("/mud/user-input/{sessionId}")
    public void greeting(@DestinationVariable String sessionId, Command message) throws Exception {
        // get the current user with the sessionId
        User currentUser = userSessions.get(sessionId);

        // find the room the user is in
        Room currentRoom = currentUser.getRoom();

        // change room based off input
        if (message.getCommand().equals("w") && currentRoom.getWest() != null) {
            currentUser.setRoom(currentRoom.getWest());
        }

        template.convertAndSendToUser(currentUser.getSessionId(), "", new StatusUpdate(currentUser.getRoom().getId()));
    }
}
