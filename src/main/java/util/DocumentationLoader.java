package util;

import entity.Documentation;
import entity.DocumentationSection;

import java.util.Collections;
import java.util.List;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;

public class DocumentationLoader{
        private static List<String> readFileInList(String fileName)
        {

            List<String> lines = Collections.emptyList();
            try {
                lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
            }

            catch (IOException e) {
                e.printStackTrace();
            }
            return lines;
        }
    public static Documentation loadDocumentationFromFile(String fileName) {
        List<String> lines = readFileInList(fileName);

        List<DocumentationSection> docSections = new ArrayList();
        for(String l: lines){
            docSections.add(new DocumentationSection(l));
        }
        return new Documentation(docSections);
    }
}
