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

        Configuration configuration = null;
        try {
            configuration = new Configuration.Builder()
                    .setName("bot")
                    .setServerPassword("")
                    .setServerHostname("sig.segv.at")
                    .setServerPort(9992)
                    .addAutoJoinChannel("#door")
                    .setSocketFactory(aDefault)
                    .addListener(new EventListener())
                    .buildConfiguration();
        } catch (Exception e) {
            e.printStackTrace();
        }


        PircBotX bot = new PircBotX(configuration);

        bot.startBot();


    }

}
