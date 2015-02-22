package at.segv.noodlebot;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import at.segv.noodlebot.actors.BotActor;
import at.segv.noodlebot.actors.MessageProcessor;
import at.segv.noodlebot.messages.Initialize;
import org.pircbotx.exception.IrcException;

import java.io.IOException;

/**
 * Created by mat on 2/11/15.
 */
public class Main {

    public static void main(String args[]) throws IOException, IrcException {


        ActorSystem botnet = ActorSystem.create("botnet");
        ActorRef actorRef = botnet.actorOf(BotActor.props(args));
        actorRef.tell(new Initialize(),null);

    }

}
