package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {
    @Autowired
    SimpMessagingTemplate template;

    @MessageMapping("/hello/{channel}")
    public void greeting(@DestinationVariable String channel, HelloMessage message) throws Exception {
        template.convertAndSend(String.format("/topic/%s", channel), new Greeting("Hello, " + message.getName() + "!"));
    }
}
