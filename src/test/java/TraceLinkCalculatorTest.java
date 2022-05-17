import entity.DocumentationSection;
import entity.ModelEntity;
import entity.TraceLink;
import org.junit.Assert;
import org.junit.Test;
import util.TraceLinkCalculator;
import java.util.ArrayList;
import java.util.List;

import static entity.SimilarityMeasure.*;

public class TraceLinkCalculatorTest {
    @Test
    public void calculateTraceLinks_entityNameInText_matchesEqualsOne(){
        List<String> entityNameParts = new ArrayList<>();
        entityNameParts.add("Test");
        entityNameParts.add("Entity");
        ModelEntity testEntity = new ModelEntity("TestEntity", entityNameParts, "test");
        DocumentationSection documentationSection = new DocumentationSection("test the TestEntity", 1);
        TraceLink t = TraceLinkCalculator.calculateTraceLink(testEntity, documentationSection,
                LEVENSHTEIN, 1.0);
        Assert.assertEquals(1, t.getMatches(), 0.0);
    }

    @Test
    public void calculateTraceLinks_entityNotInText_matchesEqualsZero(){
        List<String> entityNameParts = new ArrayList<>();
        entityNameParts.add("Another");
        entityNameParts.add("Entity");
        ModelEntity testEntity = new ModelEntity("TestEntity", entityNameParts, "test");
        DocumentationSection documentationSection = new DocumentationSection("test the AnotherEntity", 1);
        TraceLink t = TraceLinkCalculator.calculateTraceLink(testEntity, documentationSection,
                LEVENSHTEIN, 1.0);
        Assert.assertEquals(0, t.getMatches(), 0.0);
    }

    @Test
    public void calculateTraceLinks_ignoreCases_matchesEqualsZero(){
        List<String> entityNameParts = new ArrayList<>();
        entityNameParts.add("Test");
        entityNameParts.add("Entity");
        ModelEntity testEntity = new ModelEntity("TestEntity", entityNameParts, "test");
        DocumentationSection documentationSection = new DocumentationSection("test the testentity", 1);
        TraceLink t = TraceLinkCalculator.calculateTraceLink(testEntity, documentationSection,
                LEVENSHTEIN, 1.0);
        Assert.assertEquals(1, t.getMatches(), 0.0);
    }

    @Test
    public void calculateTraceLinks_bigramInText_matchesEqualsOne(){
        List<String> entityNameParts = new ArrayList<>();
        entityNameParts.add("Test");
        entityNameParts.add("Entity");
        ModelEntity testEntity = new ModelEntity("TestEntity", entityNameParts, "test");
        DocumentationSection documentationSection = new DocumentationSection("test another test entity", 1);
        TraceLink t = TraceLinkCalculator.calculateTraceLink(testEntity, documentationSection,
                LEVENSHTEIN, 1.0);
        Assert.assertEquals(1, t.getMatches(), 0.0);
    }

    @Test
    public void calculateTraceLinks_trigramInText_matchesEqualsThree(){
        List<String> entityNameParts = new ArrayList<>();
        entityNameParts.add("Another");
        entityNameParts.add("Test");
        entityNameParts.add("Entity");
        ModelEntity testEntity = new ModelEntity("AnotherTestEntity", entityNameParts, "test");
        DocumentationSection documentationSection = new DocumentationSection("test another test entity", 1);
        TraceLink t = TraceLinkCalculator.calculateTraceLink(testEntity, documentationSection,
                LEVENSHTEIN, 1.0);
        Assert.assertEquals(3, t.getMatches(), 0.0);
    }
}
