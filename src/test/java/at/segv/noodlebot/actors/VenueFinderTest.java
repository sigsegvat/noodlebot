package at.segv.noodlebot.actors;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class VenueFinderTest {

    @Test
    public void testDetect() throws Exception {
               final Props props = Props.create(VenueFinder.class);
        final TestActorRef<VenueFinder> ref = TestActorRef.create(ActorSystem.create(), props, "testA");
        final VenueFinder d = ref.underlyingActor();

        Optional<String> detected = d.detectVenue("wir gehn alle gerne zum spar");
        assertEquals(true, detected.isPresent());
    }
}