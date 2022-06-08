/* Licensed under MIT 2022. */
package io.github.ardoco.simpletracelinkdiscovery.eval;

import edu.kit.kastel.mcse.ardoco.core.model.IModelConnector;
import edu.kit.kastel.mcse.ardoco.core.model.PcmXMLModelConnector;
import io.github.ardoco.simpletracelinkdiscovery.entity.ModelEntity;
import io.github.ardoco.simpletracelinkdiscovery.entity.TraceLink;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import io.github.ardoco.simpletracelinkdiscovery.util.DocumentationLoader;
import io.github.ardoco.simpletracelinkdiscovery.util.ModelLoader;
import io.github.ardoco.simpletracelinkdiscovery.util.TraceLinkCalculator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static io.github.ardoco.simpletracelinkdiscovery.entity.SimilarityMeasure.*;

class EvaluationIT {
    @DisplayName("Evaluate TLR")
    @ParameterizedTest(name = "Evaluating {0} (Text)")
    @EnumSource(value = Project.class)
    void evaluate(Project project) throws ReflectiveOperationException, IOException {
        var repoFilePath = project.getModel();
        var documentationPath = project.getTextFile();
        var goldStandardPath = project.getGoldStandard();

        // load model
        File repoFile = new File(repoFilePath);
        IModelConnector modelConnector = new PcmXMLModelConnector(repoFile);
        ModelLoader modelLoader = new ModelLoader(modelConnector);
        List<ModelEntity> modelEntities = modelLoader.modelEntityList();

        // load documentation
        DocumentationLoader documentationLoader = new DocumentationLoader(new File(documentationPath));
        List<TraceLink> traceLinks = TraceLinkCalculator.calculateTraceLinks(documentationLoader.getDocumentationSections(), modelEntities, 0.0, LEVENSHTEIN,
                0.9);
        Collections.sort(traceLinks, Collections.reverseOrder());

        // evaluate
        GoldStandard goldstandard = new GoldStandard(new File(goldStandardPath));
        int n = goldstandard.getTotalNumberOfLinks();
        int tp = 0;
        int fp = 0;

        for (TraceLink traceLink : traceLinks) {
            if (isCorrectLink(goldstandard, traceLink)) {
                tp++;
            } else {
                fp++;
            }
        }

        // create and print result
        EvaluationResult evaluationResult = new EvaluationResult(tp, fp, (n - tp));
        Assertions.assertNotNull(evaluationResult);

        System.out.println(project.name());
        System.out.println("precision: " + evaluationResult.precision());
        System.out.println("recall: " + evaluationResult.recall());
        System.out.println("f1-score: " + evaluationResult.f1());
    }

    private Boolean isCorrectLink(GoldStandard goldstandard, TraceLink tracelink) {
        int sectionNumber = tracelink.getDocSection().getSectionNumber();
        String modelEntityId = tracelink.getModelEntity().getId();
        return goldstandard.getModelInstances(sectionNumber).contains(modelEntityId);
    }
}
