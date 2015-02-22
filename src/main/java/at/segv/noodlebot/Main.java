package at.segv.noodlebot;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import at.segv.noodlebot.actors.BotMessageSender;
import at.segv.noodlebot.actors.MessageProcessor;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.UtilSSLSocketFactory;
import org.pircbotx.exception.IrcException;

import java.io.IOException;

/**
 * Created by mat on 2/11/15.
 */
public class Main {

    public static void main(String args[]) throws IOException, IrcException {
        UtilSSLSocketFactory aDefault = new UtilSSLSocketFactory();
        aDefault.trustAllCertificates();

        ActorSystem botnet = ActorSystem.create("botnet");
        ActorRef messsageProcessor = botnet.actorOf(MessageProcessor.props());


        Configuration configuration = null;
        try {
            configuration = new Configuration.Builder()
                    .setName(args[0])
                    .setServerPassword(args[1])
                    .setServerHostname(args[2])
                    .setServerPort(Integer.parseInt(args[3]))
                    .addAutoJoinChannel(args[4])
                    .setSocketFactory(aDefault)
                    .addListener(new EventListener(messsageProcessor))
                    .buildConfiguration();
        } catch (Exception e) {
            e.printStackTrace();
        }


        PircBotX bot = new PircBotX(configuration);
        botnet.actorOf(BotMessageSender.props(bot),"sender");
        System.out.println(botnet);
        bot.startBot();






    }

}
