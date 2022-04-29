package entity;

import java.util.List;

public class Documentation {
    private List<DocumentationSection> documentationSections;
    public Documentation(List<DocumentationSection> documentationSections) {
        this.documentationSections = documentationSections;
    }
    public List<DocumentationSection> getDocumentationSections() {
        return documentationSections;
    }
}
