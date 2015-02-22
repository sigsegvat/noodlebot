package at.segv.noodlebot.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;
import at.segv.noodlebot.actors.helpers.EventListener;
import at.segv.noodlebot.messages.Initialize;
import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.UtilSSLSocketFactory;
import org.pircbotx.exception.IrcException;

import java.io.IOException;

/**
 * Created by mat on 2/22/15.
 */
public class BotActor extends UntypedActor {

    private PircBotX bot;
    private Thread botThread;

    public BotActor(String args[]){

        ActorRef messsageProcessor = getContext().actorOf(MessageProcessor.props());

        UtilSSLSocketFactory aDefault = new UtilSSLSocketFactory();
        aDefault.trustAllCertificates();
        Configuration configuration = null;
        try {
            configuration = new Configuration.Builder()
                    .setName(args[0])
                    .setServerPassword(args[1])
                    .setServerHostname(args[2])
                    .setServerPort(Integer.parseInt(args[3]))
                    .addAutoJoinChannel(args[4])
                    .setSocketFactory(aDefault)
                    .setSocketTimeout(2000)
                    .addListener(new EventListener(messsageProcessor))
                    .buildConfiguration();
        } catch (Exception e) {
            e.printStackTrace();
        }


        PircBotX bot = new PircBotX(configuration);
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
        }else if(o instanceof Initialize){
            botThread = createBotThread();
            botThread.start();
        }
    }

    public void postStop() {
        bot.stopBotReconnect();
        botThread.interrupt();
    }

    private Thread createBotThread() {
        return new Thread(){
            public void run() {
                try {
                    System.out.println("BOTID: "+this.getThreadGroup()+" "+this.getName());
                    bot.startBot();

                } catch (IOException |IrcException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static Props props(String args[]) {
        return Props.create(BotActor.class, new Creator<BotActor>() {
            @Override
            public BotActor create() throws Exception {
                return new BotActor(args);
            }
        });
    }
}
