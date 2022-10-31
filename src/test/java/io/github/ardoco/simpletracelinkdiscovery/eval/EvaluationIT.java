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
        int fn = n - tp;
        int tn = (n * modelEntities.size()) - tp - fp - fn;

        for (TraceLink traceLink : traceLinks) {
            if (isCorrectLink(goldstandard, traceLink)) {
                tp++;
            } else {
                fp++;
            }
        }

        // create and print result

        EvaluationResult evaluationResult = new EvaluationResult(tp, fp, fn, tn);
        Assertions.assertNotNull(evaluationResult);

        if (logger.isInfoEnabled()) {
            String linebreak = System.lineSeparator();
            StringBuilder logBuilder = new StringBuilder(project.name());
            logBuilder.append(linebreak);
            logBuilder.append(String.format(Locale.ENGLISH, "Precision:   %.3f", evaluationResult.precision()));
            logBuilder.append(linebreak);
            logBuilder.append(String.format(Locale.ENGLISH, "Recall:      %.3f", evaluationResult.recall()));
            logBuilder.append(linebreak);
            logBuilder.append(String.format(Locale.ENGLISH, "F1-Score:    %.3f", evaluationResult.f1()));
            logBuilder.append(linebreak);
            logBuilder.append(String.format(Locale.ENGLISH, "Accuracy:    %.3f", evaluationResult.accuracy()));
            logBuilder.append(linebreak);
            logBuilder.append(String.format(Locale.ENGLISH, "Specificity: %.3f", evaluationResult.specificity()));
            logBuilder.append(linebreak);
            logBuilder.append(String.format(Locale.ENGLISH, "Phi:         %.3f", evaluationResult.phiCoefficient()));
            logBuilder.append(linebreak);
            logBuilder.append(String.format(Locale.ENGLISH, "PhiNorm:     %.3f", evaluationResult.phiOverPhiMax()));

            logger.info(logBuilder.toString());
        }

    }

    private Boolean isCorrectLink(GoldStandard goldstandard, TraceLink tracelink) {
        int sectionNumber = tracelink.getDocSection().getSectionNumber();
        String modelEntityId = tracelink.getModelEntity().getId();
        return goldstandard.getModelInstances(sectionNumber).contains(modelEntityId);
    }
}
