import entity.DocumentationSection;
import entity.ModelEntity;
import entity.SimilarityMeasure;
import entity.TraceLink;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import util.NgramExtractor;
import util.TraceLinkCalculator;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TraceLinkCalculatorTest {
    @ParameterizedTest
    @EnumSource(SimilarityMeasure.class)
    void calculateTraceLinks_entityNameInText_matchesEqualsOne(SimilarityMeasure measure){
        List<String> entityNameParts = new ArrayList<>();
        entityNameParts.add("Test");
        entityNameParts.add("Entity");
        ModelEntity testEntity = new ModelEntity("TestEntity", entityNameParts, "test");
        DocumentationSection documentationSection = new DocumentationSection("test the TestEntity", 1);
        TraceLink t = TraceLinkCalculator.calculateTraceLink(testEntity, documentationSection,
                measure, 1.0);
        Assertions.assertEquals(1, t.getMatches(), 0.0);
    }

    @ParameterizedTest
    @EnumSource(SimilarityMeasure.class)
    void calculateTraceLinks_entityNotInText_matchesEqualsZero(SimilarityMeasure measure){
        List<String> entityNameParts = new ArrayList<>();
        entityNameParts.add("Another");
        entityNameParts.add("Entity");
        ModelEntity testEntity = new ModelEntity("TestEntity", entityNameParts, "test");
        DocumentationSection documentationSection = new DocumentationSection("test the AnotherEntity", 1);
        TraceLink t = TraceLinkCalculator.calculateTraceLink(testEntity, documentationSection,
                measure, 1.0);
        Assertions.assertEquals(0, t.getMatches(), 0.0);
    }

    @ParameterizedTest
    @EnumSource(SimilarityMeasure.class)
    void calculateTraceLinks_ignoreCases_matchesEqualsZero(SimilarityMeasure measure){
        List<String> entityNameParts = new ArrayList<>();
        entityNameParts.add("Test");
        entityNameParts.add("Entity");
        ModelEntity testEntity = new ModelEntity("TestEntity", entityNameParts, "test");
        DocumentationSection documentationSection = new DocumentationSection("test the testentity", 1);
        TraceLink t = TraceLinkCalculator.calculateTraceLink(testEntity, documentationSection,
                measure, 1.0);
        Assertions.assertEquals(1, t.getMatches(), 0.0);
    }

    @ParameterizedTest
    @EnumSource(SimilarityMeasure.class)
    void calculateTraceLinks_bigramInText_matchesEqualsOne(SimilarityMeasure measure){
        List<String> entityNameParts = new ArrayList<>();
        entityNameParts.add("Test");
        entityNameParts.add("Entity");
        ModelEntity testEntity = new ModelEntity("TestEntity", entityNameParts, "test");
        DocumentationSection documentationSection = new DocumentationSection("test another test entity", 1);
        TraceLink t = TraceLinkCalculator.calculateTraceLink(testEntity, documentationSection,
                measure, 1.0);
        Assertions.assertEquals(1, t.getMatches(), 0.0);
    }

    @ParameterizedTest
    @EnumSource(SimilarityMeasure.class)
    void calculateTraceLinks_trigramInText_matchesEqualsThree(SimilarityMeasure measure){
        List<String> entityNameParts = new ArrayList<>();
        entityNameParts.add("Another");
        entityNameParts.add("Test");
        entityNameParts.add("Entity");
        ModelEntity testEntity = new ModelEntity("AnotherTestEntity", entityNameParts, "test");
        DocumentationSection documentationSection = new DocumentationSection("test another test entity", 1);
        TraceLink t = TraceLinkCalculator.calculateTraceLink(testEntity, documentationSection,
                measure, 1.0);
        Assertions.assertEquals(3, t.getMatches(), 0.0);
    }
}
