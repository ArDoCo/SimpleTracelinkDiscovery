import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import util.DocumentationLoader;

import java.io.File;

public class DocumentationLoaderTest {
    public static DocumentationLoader documentationLoader;

    @BeforeClass
    public static void setUp() {
        documentationLoader = new DocumentationLoader(new File("./src/test/resources/teastore/teastore.txt"));
    }

    @Test
    public void load_sectionNumber_startsWithOne(){
        int n = documentationLoader.getDocumentationSections().get(0).getSectionNumber();
        Assert.assertEquals(1, n);
    }

    @Test
    public void load_sectionNumber_oneMoreThanListIndex(){
        for(int i = 0; i<documentationLoader.getDocumentationSections().size(); i++){
            int n = documentationLoader.getDocumentationSections().get(i).getSectionNumber();
            Assert.assertEquals(n, (i+1));
        }
    }
}
