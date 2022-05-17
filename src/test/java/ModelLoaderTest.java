import edu.kit.kastel.mcse.ardoco.core.model.IModelConnector;
import edu.kit.kastel.mcse.ardoco.core.model.pcm.PcmXMLModelConnector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.ModelLoader;

import java.io.File;
import java.io.IOException;

public class ModelLoaderTest {
    public static ModelLoader modelLoader;
    @BeforeAll
    public static void setUp() throws ReflectiveOperationException, IOException {
        File repoFile = new File("./src/test/resources/teastore/original_model/teastore.repository");
        IModelConnector modelConnector = new PcmXMLModelConnector(repoFile);
        modelLoader = new ModelLoader(modelConnector);
    }

    @Test
    public void modelEntityList_entityList_notNull(){
        Assertions.assertNotNull(modelLoader.modelEntityList());
    }
}
