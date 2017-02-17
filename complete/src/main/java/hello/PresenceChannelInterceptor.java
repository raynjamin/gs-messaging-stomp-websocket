package hello;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

public class PresenceChannelInterceptor extends ChannelInterceptorAdapter {

    private final Log logger = LogFactory.getLog(PresenceChannelInterceptor.class);

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);

        String sessionId = sha.getSessionId();

        StompCommand cmd = sha.getCommand();

        if (cmd != null && cmd.equals(StompCommand.CONNECT)) {

        }

        return super.preSend(message, channel);
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);

        String sessionId = sha.getSessionId();

        StompCommand cmd = sha.getCommand();

        if (cmd != null) {
            switch (sha.getCommand()) {
                case CONNECT:
                    GreetingController.userSessions.put(sessionId, new User(sessionId));
                    System.out.println("STOMP Connect [sessionId: " + sessionId + "]");
                    break;
                case CONNECTED:
                    System.out.println("STOMP Connected [sessionId: " + sessionId + "]");
                    break;
                case DISCONNECT:
                    System.out.println("STOMP Disconnect [sessionId: " + sessionId + "]");
                    System.out.println("STOMP THING: " + sha.getHeader("stompCommand"));
                    break;
                default:
                    break;
            }
        } else {
            logger.debug(sha.getHeader("stompCommand"));
        }
    }
}