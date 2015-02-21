package at.segv.noodlebot.processors;

import at.segv.noodlebot.messages.Event;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class VenueDetectorTest {

    @Test
    public void testDetect() throws Exception {
        VenueDetector d = new VenueDetector();

        Optional<Event> detected = d.detect("wir gehn alle gerne zum spar");
        assertEquals(true, detected.isPresent());
    }
}