/* Licensed under MIT 2022. */
package io.github.ardoco.simpletracelinkdiscovery;

import edu.kit.kastel.mcse.ardoco.core.api.data.model.ModelConnector;
import edu.kit.kastel.mcse.ardoco.core.model.PcmXMLModelConnector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.github.ardoco.simpletracelinkdiscovery.util.ModelLoader;

import java.io.File;
import java.io.IOException;

class ModelLoaderTest {
    public static ModelLoader modelLoader;

    @BeforeAll
    public static void setUp() throws ReflectiveOperationException, IOException {
        File repoFile = new File("./src/test/resources/benchmark/teastore/model_2020/pcm/teastore.repository");
        ModelConnector modelConnector = new PcmXMLModelConnector(repoFile);
        modelLoader = new ModelLoader(modelConnector);
    }

    @Test
    void modelEntityList_entityList_notNull() {
        Assertions.assertNotNull(modelLoader.modelEntityList());
    }
}
