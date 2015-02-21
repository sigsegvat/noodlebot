package at.segv.noodlebot.processors;


import at.segv.noodlebot.messages.EatEvent;
import at.segv.noodlebot.messages.Event;

import java.util.Optional;

public class VenueDetector {

    private final SpeechProcessor speechProcessor;

    private static final String[] venues = {"noodlinger", "spar"};

    public VenueDetector(){
        speechProcessor = new SpeechProcessor();
    }

    public Optional<Event> detect(String sentence){
        EventType eventType = speechProcessor.analyzeSentence(sentence);
        Optional<String> s = detectVenue(sentence);
        if( eventType == EventType.EatEvent && s.isPresent()){

            return Optional.of(new EatEvent(sentence, s.get()));
        }
        else {
            return Optional.empty();
        }
    }

    private Optional<String> detectVenue(String input){
        String lowercaseinput = input.toLowerCase();
        for(String venue: venues){

            if(lowercaseinput.contains(venue)) return Optional.of(venue);
        }
        return Optional.empty();
    }



}
