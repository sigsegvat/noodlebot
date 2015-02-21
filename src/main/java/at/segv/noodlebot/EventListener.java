package at.segv.noodlebot;

import at.segv.noodlebot.messages.EatEvent;
import at.segv.noodlebot.processors.EventType;
import at.segv.noodlebot.processors.VenueDetector;
import org.pircbotx.User;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.output.OutputChannel;

import java.util.*;

/**
* Created by mat on 2/11/15.
*/
class EventListener extends ListenerAdapter {

    private Map<String,Set<User>> venueMap= new HashMap<>();

    private VenueDetector venueDetector = new VenueDetector();

    @Override
    public void onEvent(Event event) throws Exception {
        super.onEvent(event);
        System.out.println(event);
    }


    @Override
    public void onMessage(MessageEvent event)  throws Exception{
        String message = event.getMessage();

        Optional<at.segv.noodlebot.messages.Event> detected = venueDetector.detect(message);

       if(message.startsWith("wohin essen?")){


            for(String venue: venueMap.keySet()){
                StringBuilder b = new StringBuilder("Geh doch zum " + venue + " mit ");
                Set<User> users = venueMap.get(venue);
                for(User u: users){
                    b.append(u.getNick()+" ");
                }
                if(users.size()>0)
                    event.getChannel().send().message(b.toString());
            };

        }
        else {
            addVenue(event, message);
        }
    }

    private void addVenue(MessageEvent event, String message) {
        Optional<at.segv.noodlebot.messages.Event> detected = venueDetector.detect(message);
        if(detected.isPresent() && detected.get().getClass() == EatEvent.class){
            EatEvent eatEvent = (EatEvent) detected.get();


            addToMap(event.getUser(),eatEvent.getVenue());
        }

    }

    private void addToMap(User user, String venue){
        if(!venueMap.containsKey(venue)){
             venueMap.put(venue,new HashSet<>());
        }

        venueMap.get(venue).add(user);

    }
}
