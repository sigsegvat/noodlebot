package at.segv.noodlebot.actors;


import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import at.segv.noodlebot.messages.EatEvent;

import static org.junit.Assert.assertEquals;

public class EventClassifierTest {

    @org.junit.Test
    public void testAnalyzeSentence() throws Exception {

        final Props props = Props.create(EventClassifier.class);
        final TestActorRef<EventClassifier> ref = TestActorRef.create(ActorSystem.create(), props, "testA");
        final EventClassifier p = ref.underlyingActor();

        assertEquals(EatEvent.class, p.analyzeSentence("ich glaub ich geh zum spar").getClass());

        assertEquals(EatEvent.class, p.analyzeSentence("werd mal spar gehen").getClass());




    }
}