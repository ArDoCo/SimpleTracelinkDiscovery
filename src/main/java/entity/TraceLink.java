package entity;
public class TraceLink implements Comparable<TraceLink>{
    private ModelEntity modelEntity;
    private DocumentationSection docSection;
    private double confidence;

    public TraceLink(ModelEntity entity, DocumentationSection docSection, double confidence) {
        this.modelEntity = entity;
        this.docSection = docSection;
        this.confidence = confidence;
    }

    public ModelEntity getModelEntity() {
        return modelEntity;
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
