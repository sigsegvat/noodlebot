package at.segv.noodlebot.actors;

import akka.actor.Props;
import akka.actor.UntypedActor;
import at.segv.noodlebot.messages.EatEvent;
import at.segv.noodlebot.messages.Event;
import at.segv.noodlebot.messages.MessageEvent;
import at.segv.noodlebot.messages.VenueEatEvent;

/**
 * Created by mat on 2/22/15.
 */
public class MessageProcessor extends UntypedActor{

    public static final String MSG_PROC = "msgProc";
    public static final String VEN_DET = "venDet";


    public MessageProcessor() {
        this.getContext().actorOf(EventClassifier.props(), MSG_PROC);
        this.getContext().actorOf(VenueFinder.props(), VEN_DET);
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof MessageEvent){
           getContext().getChild(MSG_PROC).tell(((Event) o).getText(),self());
        }else if(o instanceof VenueEatEvent){
            getContext().actorSelection("../sender").tell("venue found: "+((VenueEatEvent) o).getVenue(),self());
        }else if(o instanceof EatEvent){
            getContext().getChild(VEN_DET).tell(o,self());
        }
    }

    public static Props props() {
        return Props.create(MessageProcessor.class,MessageProcessor::new)   ;
    }


}
