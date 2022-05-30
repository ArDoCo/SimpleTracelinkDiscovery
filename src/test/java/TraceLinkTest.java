import entity.DocumentationSection;
import entity.ModelEntity;
import entity.TraceLink;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
class TraceLinkTest {
    @Test
    void equals_equalTraceLink_true(){
        ModelEntity entity = new ModelEntity(null, null, "entity1");
        DocumentationSection docSection = new DocumentationSection(null, 1);
        TraceLink tl1 = new TraceLink(entity, docSection, 1.0);
        TraceLink tl2 = new TraceLink(entity, docSection, 1.0);
        Assertions.assertEquals(true, tl1.equals(tl2));
        Assertions.assertEquals(true, tl2.equals(tl1));
    }

    @Test
    void equals_differentMatchcount_false(){
        ModelEntity entity = new ModelEntity(null, null, "entity1");
        DocumentationSection docSection = new DocumentationSection(null, 1);
        TraceLink tl1 = new TraceLink(entity, docSection, 1.0);
        TraceLink tl2 = new TraceLink(entity, docSection, 2.0);
        Assertions.assertEquals(false, tl1.equals(tl2));
        Assertions.assertEquals(false, tl2.equals(tl1));
    }

    @Test
    void equals_differentEntityIdOrSectionNumber_false(){
        ModelEntity entity1 = new ModelEntity(null, null, "entity1");
        ModelEntity entity2 = new ModelEntity(null, null, "entity2");
        DocumentationSection docSection1 = new DocumentationSection(null, 1);
        DocumentationSection docSection2 = new DocumentationSection(null, 2);

        TraceLink tl1 = new TraceLink(entity1, docSection1, 1.0);
        TraceLink tl2 = new TraceLink(entity2, docSection1, 1.0);

        Assertions.assertEquals(false, tl1.equals(tl2));
        Assertions.assertEquals(false, tl2.equals(tl1));

        TraceLink tl3 = new TraceLink(entity1, docSection1, 1.0);
        TraceLink tl4 = new TraceLink(entity1, docSection2, 1.0);

        Assertions.assertEquals(false, tl3.equals(tl4));
        Assertions.assertEquals(false, tl4.equals(tl3));
    }
}
