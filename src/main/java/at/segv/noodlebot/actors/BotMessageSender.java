package at.segv.noodlebot.actors;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;

/**
 * Created by mat on 2/22/15.
 */
public class BotMessageSender extends UntypedActor {

    private PircBotX bot;

    public BotMessageSender(PircBotX bot){

        this.bot = bot;
    }

    public void sendMessage(String message){
        for (Channel channel : bot.getUserBot().getChannels()) {
            channel.send().message(message);
        }
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof String){
            sendMessage((String) o);
        }
    }

    public static Props props(PircBotX bot) {
        return Props.create(BotMessageSender.class, new Creator<BotMessageSender>() {
            @Override
            public BotMessageSender create() throws Exception {
                return new BotMessageSender(bot);
            }
        });
    }
}
