package at.segv.noodlebot.processors;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import at.segv.noodlebot.messages.Event;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

public class SpeechProcessor {


    private DocumentCategorizerME myCategorizer = null;
    private DoccatModel documentModel = null;


    public SpeechProcessor() {


        try (InputStream dataIn = ClassLoader.getSystemResourceAsStream("spar.nlp");) {

            ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

            documentModel = DocumentCategorizerME.train("de", sampleStream);
            myCategorizer = new DocumentCategorizerME(documentModel);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Optional<EventType> analyzeSentence(String sentence) {
        String bestCategory = myCategorizer.getBestCategory(myCategorizer.categorize(sentence));

        if("EatEvent".equals(bestCategory)){
            return  Optional.of(EventType.EatEvent);
        }
        else {
            return Optional.empty();
        }
    }

}
