package at.segv.noodlebot;


import at.segv.noodlebot.messages.Event;
import at.segv.noodlebot.processors.EventType;
import at.segv.noodlebot.processors.SpeechProcessor;

import static org.junit.Assert.assertEquals;

public class SpeechProcessorTest {

    @org.junit.Test
    public void testAnalyzeSentence() throws Exception {
        SpeechProcessor p = new SpeechProcessor();
        assertEquals(EventType.EatEvent, p.analyzeSentence("ich glaub ich geh zum spar").get());



    }
}