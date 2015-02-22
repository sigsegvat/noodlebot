package at.segv.noodlebot;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import at.segv.noodlebot.messages.Event;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
* Created by mat on 2/11/15.
*/
public class EventListener extends ListenerAdapter {

    private final ActorRef messageProcessor;

    public EventListener(ActorRef messageProcessor){
        super();
        this.messageProcessor = messageProcessor;
    }




    @Override
    public void onMessage(MessageEvent event)  throws Exception{
        String message = event.getMessage();
        messageProcessor.tell(new at.segv.noodlebot.messages.MessageEvent(message), null);
    }




}
