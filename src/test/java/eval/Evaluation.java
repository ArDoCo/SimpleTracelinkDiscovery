package eval;

import edu.kit.kastel.mcse.ardoco.core.model.IModelConnector;
import edu.kit.kastel.mcse.ardoco.core.model.pcm.PcmXMLModelConnector;
import entity.ModelEntity;
import entity.TraceLink;
import org.junit.jupiter.api.Test;
import util.DocumentationLoader;
import util.ModelLoader;
import util.TraceLinkCalculator;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static entity.SimilarityMeasure.*;

public class Evaluation {
    
    private String repoFilePath = "./src/test/resources/mediastore/original_model/ms.repository";
    private String goldstandardPath = "./src/test/resources/mediastore/goldstandard.csv";
    private String documentationPath = "./src/test/resources/mediastore/mediastore.txt";

    @Test
    public void evaluate() throws ReflectiveOperationException, IOException {

        //load model
        File repoFile = new File(repoFilePath);
        IModelConnector modelConnector = new PcmXMLModelConnector(repoFile);
        ModelLoader modelLoader = new ModelLoader(modelConnector);
        List<ModelEntity> modelEntities = modelLoader.modelEntityList();

        //load documentation
        DocumentationLoader documentationLoader = new DocumentationLoader(new File(documentationPath));
        List<TraceLink> traceLinks = TraceLinkCalculator.calculateTraceLinks(documentationLoader.getDocumentationSections(),
                modelEntities, 0.0, LEVENSHTEIN, 0.9);
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
        int sectionNumber = tracelink.getDocSection().getSectionNumber();
        String modelEntityId = tracelink.getModelEntity().getId();
        return goldstandard.getModelInstances(sectionNumber).contains(modelEntityId);
    }
}
