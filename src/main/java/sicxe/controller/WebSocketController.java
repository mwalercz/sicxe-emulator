package sicxe.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import sicxe.view.websocket.Greeting;
import sicxe.view.websocket.InfoMessage;

/**
 * Created by maciek on 16.11.15.
 */
@Controller
public class WebSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(InfoMessage message) throws Exception{
        Thread.sleep(3000);
        return new Greeting("Hello," +message.getName() + "!");
    }
}
