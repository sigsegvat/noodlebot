package at.segv.noodlebot.actors;

import java.io.IOException;
import java.io.InputStream;

import akka.actor.Props;
import akka.actor.UntypedActor;
import at.segv.noodlebot.messages.EatEvent;
import at.segv.noodlebot.messages.Event;
import at.segv.noodlebot.messages.WhereEatEvent;
import opennlp.tools.doccat.*;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

public class EventClassifier extends UntypedActor {


    public static final String EAT_EVENT = "EatEvent";
    public static final String WHERE_EAT_EVENT = "WhereEatEvent";
    private DocumentCategorizerME myCategorizer = null;
    private DoccatModel documentModel = null;


    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof String){
            Event event = analyzeSentence((String) o);
            getSender().tell(event,self());
        }
    }

    public static Props props() {
        return Props.create(EventClassifier.class,EventClassifier::new);
    }

    public EventClassifier() {


        try (InputStream dataIn = ClassLoader.getSystemResourceAsStream("spar.nlp");) {

            ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
            documentModel = DocumentCategorizerME.train("de", sampleStream, 5, 100);
            myCategorizer = new DocumentCategorizerME(documentModel);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Event analyzeSentence(String message) {

        double[] categorize = myCategorizer.categorize(message);
        String bestCategory = myCategorizer.getBestCategory(categorize);
        double bestProb = categorize[myCategorizer.getIndex(bestCategory)];

        if(bestProb<=0.5) return new Event(message);

        if(EAT_EVENT.equals(bestCategory)){
            return  new EatEvent(message);
        }
        else if(WHERE_EAT_EVENT.equals(bestCategory)){
            return  new WhereEatEvent(message);
        }
        else {
            return new Event( message);
        }
    }

}
