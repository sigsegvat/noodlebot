package at.segv.noodlebot.messages;

/**
 * Created by mat on 2/22/15.
 */
public class VenueEatEvent extends EatEvent {

    private final String venue;

    public VenueEatEvent(String text, String venue) {
        super(text);
        this.venue = venue;
    }

    public String getVenue() {
        return venue;
    }
}
