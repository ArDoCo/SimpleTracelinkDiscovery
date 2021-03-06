/* Licensed under MIT 2022. */
package io.github.ardoco.simpletracelinkdiscovery.entity;

public class TraceLink implements Comparable<TraceLink> {
    private final ModelEntity modelEntity;
    private final DocumentationSection docSection;
    private double matches;

    public TraceLink(ModelEntity entity, DocumentationSection docSection, double matches) {
        this.modelEntity = entity;
        this.docSection = docSection;
        this.matches = matches;
    }

    public ModelEntity getModelEntity() {
        return modelEntity;
    }

    public DocumentationSection getDocSection() {
        return docSection;
    }

    public double getMatches() {
        return matches;
    }

    public void setMatches(double matches) {
        this.matches = matches;
    }

    @Override
    public int compareTo(TraceLink t) {
        return Double.compare(this.matches, t.getMatches());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof TraceLink)) {
            return false;
        }

        TraceLink t = (TraceLink) o;
        return (this.getMatches() == t.getMatches() && this.getDocSection().getSectionNumber() == t.getDocSection().getSectionNumber()
                && this.getModelEntity().getId().equals(t.getModelEntity().getId()));
    }

    @Override
    public int hashCode() {
        String idString = getDocSection().getSectionNumber() + "##" + this.getModelEntity().getId() + "##" + this.getMatches();
        return idString.hashCode();
    }
}
