package entity;

public class DocumentationSection
{
    private String text;
    private int lineNumber;
    public DocumentationSection(String text, int lineNumber) {
        this.text = text;
        this.lineNumber = lineNumber;
    }

    public String getText() {
        return text;
    }
    public int getLineNumber() {
        return lineNumber;
    }
}
