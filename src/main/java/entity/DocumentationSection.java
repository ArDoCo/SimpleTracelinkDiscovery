package entity;

public class DocumentationSection
{
    private String text;
    private int sectionNumber;
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
