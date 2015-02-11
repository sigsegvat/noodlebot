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


    static final Set<String> venues = new HashSet<>();

    private Map<String,Set<User>> venueMap= new HashMap<>();

    @Override
    public void onEvent(Event event) throws Exception {
        super.onEvent(event);
        System.out.println(event);
    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        event.respond("wow");


    }

    @Override
    public void onMessage(MessageEvent event)  throws Exception{
        String message = event.getMessage();

        if(message.startsWith("kennst du schon ") && message.endsWith("?")){
            String newVenue = message.substring(16,message.length()-1);
            if(venues.contains(newVenue)){
                event.respond("ja, "+newVenue+" kenn ich schon");
            }else {
                event.respond("wow, "+newVenue+" kenn ich no ned!");
                venues.add(newVenue);
            }
        }
        else if(message.startsWith("wohin essen?")){


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
        for(String venue:venues){
            if(message.toLowerCase().contains(venue)){
                event.getChannel().send().action(event.getUser().getNick()+" geht zum "+venue);
                addToMap(event.getUser(),venue);

                StringBuilder others=new StringBuilder();
                for(User u: venueMap.get(venue)){
                    if(!u.equals(event.getUser())) {
                        others.append(u.getNick() + " ");
                    }
                };
                if(others.length()==0){
                    others.append("niemand");
                }
                event.getChannel().send().action("Zum "+venue+" gehn sonst noch: "+others);

                break;
            }
        }
    }

    private void addToMap(User user, String venue){
        if(!venueMap.containsKey(venue)){
             venueMap.put(venue,new HashSet<>());
        }

        venueMap.get(venue).add(user);

    }
}
