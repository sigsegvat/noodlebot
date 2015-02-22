package at.segv.noodlebot.actors;


import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.testkit.TestActorRef;
import at.segv.noodlebot.messages.EatEvent;
import at.segv.noodlebot.messages.Event;
import scala.concurrent.Future;

import static org.junit.Assert.assertEquals;

public class EventClassifierTest {

    @org.junit.Test
    public void testAnalyzeSentence() throws Exception {

        final Props props = Props.create(EventClassifier.class);
        final TestActorRef<EventClassifier> ref = TestActorRef.create(ActorSystem.create(), props, "testA");
        final EventClassifier p = ref.underlyingActor();

        assertEquals(EatEvent.class, p.analyzeSentence("glaub i geh zum spar").getClass());

        assertEquals(EatEvent.class, p.analyzeSentence("mag zum nudlinger").getClass());


        Future<Object> future = Patterns.ask(ref, "The quick brown fox jumps over the lazy dog", 1000);
        Object answer = future.value().get().get();
        assertEquals(Event.class,answer.getClass());


    }
}