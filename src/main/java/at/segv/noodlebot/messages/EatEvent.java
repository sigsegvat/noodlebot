package at.segv.noodlebot.messages;


public class EatEvent extends Event {

    private String venue;

    public EatEvent(String text, String venue){
        super(text);
        this.venue = venue;
    }

    public String getVenue() {
        return venue;
    }
}
