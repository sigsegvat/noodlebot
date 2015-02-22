package at.segv.noodlebot.actors;


import akka.actor.Props;
import akka.actor.UntypedActor;
import at.segv.noodlebot.messages.EatEvent;
import at.segv.noodlebot.messages.VenueEatEvent;


import java.util.Optional;

public class VenueFinder extends UntypedActor{


    private static final String[] venues = {"noodlinger", "spar"};


    public EatEvent detect(EatEvent eatEvent) {

        Optional<String> s = detectVenue(eatEvent.getText());
        if (s.isPresent()) {

            return new VenueEatEvent(eatEvent.getText(), s.get());
        } else {
            return eatEvent;
        }
    }

    public Optional<String> detectVenue(String input) {
        String lowercaseinput = input.toLowerCase();
        for (String venue : venues) {

            if (lowercaseinput.contains(venue)) return Optional.of(venue);
        }
        return Optional.empty();
    }


    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof EatEvent){
            EatEvent detected = detect((EatEvent) o);
            getSender().tell(detected,getSelf());
        }
    }

    public static Props props() {
        return Props.create(VenueFinder.class, VenueFinder::new);
    }
}
