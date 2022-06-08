/* Licensed under MIT 2022. */
package io.github.ardoco.simpletracelinkdiscovery.entity;

public class DocumentationSection {
    private final String text;
    private final int sectionNumber;

    public DocumentationSection(String text, int sectionNumber) {
        this.text = text;
        this.sectionNumber = sectionNumber;
    }

    public String getText() {
        return text;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }
}
