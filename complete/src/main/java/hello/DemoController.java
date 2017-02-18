package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.HashMap;
import java.util.Map;

@Controller
public class DemoController {
    Map<String, User> users = new HashMap<>();

    @Autowired
    SimpMessagingTemplate template;

    @MessageMapping("/user-input")
    public void greeting(Message message, UserInput input) throws Exception {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
        input.setUser(users.get(sha.getSessionId()));

        // send to everyone else.
        // for send to everyone, remove the if.
        for(String session : users.keySet()) {
            if (session.equals(sha.getSessionId())) {
                continue;
            }

            template.convertAndSendToUser(session, "/", input);
        }
    }

    @EventListener
    public void stompEventHandler(AbstractSubProtocolEvent ev) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(ev.getMessage());

        if (ev instanceof SessionConnectEvent) {
            System.out.println("Connect event [sessionId: " + sha.getSessionId() +"]");
            String name = sha.getNativeHeader("name").get(0);

            users.put(sha.getSessionId(), new User(sha.getSessionId(), name));
        } else if (ev instanceof SessionDisconnectEvent) {
            System.out.println("Disconnect Event: [sessionId: " + sha.getSessionId() +"]");
            users.remove(sha.getSessionId());
        } else if (ev instanceof SessionSubscribeEvent) {
            System.out.println("Client Channel Subscription");
        } else {
            System.out.println("Other Event:" + ev.getClass());
        }
    }
}
