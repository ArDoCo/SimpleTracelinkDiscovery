package entity;

import util.TraceLinkCalculator;

public class TraceLink implements Comparable<TraceLink>{
    private ModelEntity entity;
    private DocumentationSection docSection;
    private double confidence;

    public TraceLink(ModelEntity entity, DocumentationSection docSection, double confidence) {
        this.entity = entity;
        this.docSection = docSection;
        this.confidence = confidence;
    }

    public ModelEntity getEntity() {
        return entity;
    }

    public DocumentationSection getDocSection() {
        return docSection;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    @Override
    public int compareTo(TraceLink o) {
        return Double.valueOf(this.confidence).compareTo(Double.valueOf(o.getConfidence()));
    }
}
