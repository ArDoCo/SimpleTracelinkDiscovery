/* Licensed under MIT 2022. */
package io.github.ardoco.simpletracelinkdiscovery.util;

import io.github.ardoco.simpletracelinkdiscovery.entity.DocumentationSection;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentationLoader {

    private final File documentationFile;
    private static final Logger LOGGER = Logger.getLogger(DocumentationLoader.class.getName());
    private final List<DocumentationSection> documentationSections;

    public DocumentationLoader(File documentationFile) {
        this.documentationFile = documentationFile;
        documentationSections = new ArrayList<>();
        load();
    }

    private void load() {
        try {
            Scanner scanner = new Scanner(documentationFile, StandardCharsets.UTF_8.name());
            int i = 1;
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                if (!nextLine.isBlank()) {
                    documentationSections.add(new DocumentationSection(nextLine, i++));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING, "Failed to load or find file.", e);
        }
    }

    public List<DocumentationSection> getDocumentationSections() {
        return documentationSections;
    }

    public DocumentationSection getDocumentationSection(int sectionNumber) {
        return documentationSections.get((sectionNumber - 1));
    }
}
