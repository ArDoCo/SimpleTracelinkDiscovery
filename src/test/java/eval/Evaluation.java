package eval;

import edu.kit.kastel.mcse.ardoco.core.model.IModelConnector;
import edu.kit.kastel.mcse.ardoco.core.model.pcm.PcmXMLModelConnector;
import entity.Documentation;
import entity.ModelEntity;
import entity.TraceLink;
import org.junit.Test;
import util.DocumentationLoader;
import util.ModelLoader;
import util.TraceLinkCalculator;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Evaluation {
    
    private String repoFilePath = "./src/test/resources/teastore/original_model/teastore.repository";
    private String goldstandardPath = "./src/test/resources/teastore/goldstandard.csv";
    private String documentationPath = "./src/test/resources/teastore/teastore.txt";

    @Test
    public void evaluate() throws ReflectiveOperationException, IOException {

        //load model
        File repoFile = new File(repoFilePath);
        IModelConnector modelConnector = new PcmXMLModelConnector(repoFile);
        ModelLoader modelLoader = new ModelLoader(modelConnector);
        List<ModelEntity> modelEntities = modelLoader.modelEntityList();

        //load documentation
        Documentation doc = DocumentationLoader.loadDocumentationFromFile(documentationPath);
        List<TraceLink> traceLinks = TraceLinkCalculator.calculateTraceLinks(doc, modelEntities, 0.0);
        Collections.sort(traceLinks, Collections.reverseOrder());

        //evaluate
        GoldStandard goldstandard = new GoldStandard(new File(goldstandardPath));
        int n = goldstandard.getTotalNumberOfLinks();
        int tp = 0;
        int fp = 0;

        for(TraceLink traceLink: traceLinks){
            if(isCorrectLink(goldstandard, traceLink)){
                tp++;
            } else {
                fp++;
            }
        }

        //create and print result
        EvaluationResult evaluationResult = new EvaluationResult(tp, fp, (n-tp));

        System.out.println("precision: " + evaluationResult.precision());
        System.out.println("recall: " + evaluationResult.recall());
        System.out.println("f1-score: " + evaluationResult.f1());

    }

    private Boolean isCorrectLink(GoldStandard goldstandard, TraceLink tracelink){
        int lineNumber = tracelink.getDocSection().getLineNumber();
        String modelEntityId = tracelink.getModelEntity().getId();

        return goldstandard.getModelInstances(lineNumber).contains(modelEntityId);
    }
}
