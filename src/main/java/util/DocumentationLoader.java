package util;

import entity.DocumentationSection;

import java.util.List;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentationLoader{

    private File documentationFile;
    private static final Logger LOGGER = Logger.getLogger(DocumentationLoader.class.getName());
    private List<DocumentationSection> documentationSections;

    public DocumentationLoader(File documentationFile) {
        this.documentationFile = documentationFile;
        documentationSections = new ArrayList<>();
        load();
    }

    private void load(){
        try {
            Scanner scanner = new Scanner(documentationFile);
            int i = 1;
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                if(!nextLine.isBlank()) {
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
    public DocumentationSection getDocumentationSection(int sectionNumber){
        return documentationSections.get((sectionNumber - 1));
    }
}
