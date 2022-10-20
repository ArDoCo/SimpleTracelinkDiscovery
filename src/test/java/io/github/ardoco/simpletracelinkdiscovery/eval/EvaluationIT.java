/* Licensed under MIT 2022. */
package io.github.ardoco.simpletracelinkdiscovery.eval;

import static io.github.ardoco.simpletracelinkdiscovery.entity.SimilarityMeasure.LEVENSHTEIN;

import io.github.ardoco.simpletracelinkdiscovery.entity.ModelEntity;
import io.github.ardoco.simpletracelinkdiscovery.entity.TraceLink;
import io.github.ardoco.simpletracelinkdiscovery.util.DocumentationLoader;
import io.github.ardoco.simpletracelinkdiscovery.util.ModelLoader;
import io.github.ardoco.simpletracelinkdiscovery.util.TraceLinkCalculator;
import edu.kit.kastel.mcse.ardoco.core.api.data.model.ModelConnector;
import edu.kit.kastel.mcse.ardoco.core.model.PcmXMLModelConnector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

class EvaluationIT {
    private static final Logger logger = LoggerFactory.getLogger(EvaluationIT.class);

    @DisplayName("Evaluate TLR")
    @ParameterizedTest(name = "Evaluating {0} (Text)")
    @EnumSource(value = Project.class)
    void evaluate(Project project) throws ReflectiveOperationException, IOException {
        var repoFilePath = project.getModel();
        var documentationPath = project.getTextFile();
        var goldStandardPath = project.getGoldStandard();

        // load model
        File repoFile = new File(repoFilePath);
        ModelConnector modelConnector = new PcmXMLModelConnector(repoFile);
        ModelLoader modelLoader = new ModelLoader(modelConnector);
        List<ModelEntity> modelEntities = modelLoader.modelEntityList();

        // load documentation
        DocumentationLoader documentationLoader = new DocumentationLoader(new File(documentationPath));
        List<TraceLink> traceLinks = TraceLinkCalculator.calculateTraceLinks(documentationLoader.getDocumentationSections(), modelEntities, 0.0, LEVENSHTEIN,
                0.9);
        traceLinks.sort(Collections.reverseOrder());

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

        if (logger.isInfoEnabled()) {
            String logBuilder = project.name() //
                    + "\nPrecision: " + String.format(Locale.ENGLISH, "%.3f", evaluationResult.precision()) //
                    + "\nRecall:    " + String.format(Locale.ENGLISH, "%.3f", evaluationResult.recall()) //
                    + "\nF1-Score:  " + String.format(Locale.ENGLISH, "%.3f", evaluationResult.f1()) //
            ;
            logger.info(logBuilder);
        }

    }

    private Boolean isCorrectLink(GoldStandard goldstandard, TraceLink tracelink) {
        int sectionNumber = tracelink.getDocSection().getSectionNumber();
        String modelEntityId = tracelink.getModelEntity().getId();
        return goldstandard.getModelInstances(sectionNumber).contains(modelEntityId);
    }
}
