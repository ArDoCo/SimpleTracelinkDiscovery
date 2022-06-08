/* Licensed under MIT 2022. */
package io.github.ardoco.simpletracelinkdiscovery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.github.ardoco.simpletracelinkdiscovery.util.DocumentationLoader;

import java.io.File;

class DocumentationLoaderTest {
    public static DocumentationLoader documentationLoader;

    @BeforeAll
    public static void setUp() {
        documentationLoader = new DocumentationLoader(new File("./src/test/resources/teastore/teastore.txt"));
    }

    @Test
    void load_sectionNumber_startsWithOne() {
        int n = documentationLoader.getDocumentationSections().get(0).getSectionNumber();
        Assertions.assertEquals(1, n);
    }

    @Test
    void load_sectionNumber_oneMoreThanListIndex() {
        for (int i = 0; i < documentationLoader.getDocumentationSections().size(); i++) {
            int n = documentationLoader.getDocumentationSections().get(i).getSectionNumber();
            Assertions.assertEquals(n, (i + 1));
        }
    }
}
