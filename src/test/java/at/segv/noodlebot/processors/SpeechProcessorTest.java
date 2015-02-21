package at.segv.noodlebot.processors;


import at.segv.noodlebot.messages.Event;
import at.segv.noodlebot.processors.EventType;
import at.segv.noodlebot.processors.SpeechProcessor;

import static org.junit.Assert.assertEquals;

public class SpeechProcessorTest {

    @org.junit.Test
    public void testAnalyzeSentence() throws Exception {
        SpeechProcessor p = new SpeechProcessor();

        assertEquals(EventType.EatEvent, p.analyzeSentence("ich glaub ich geh zum spar"));

        assertEquals(EventType.EatEvent, p.analyzeSentence("werd mal spar gehen"));

        assertEquals(EventType.UnknownEvent, p.analyzeSentence("werd mal aufs klo gehn"));

        assertEquals(EventType.UnknownEvent, p.analyzeSentence("ich glaub ich builde mal dev"));



    }
}