package entity;
public class TraceLink implements Comparable<TraceLink>{
    private ModelEntity modelEntity;
    private DocumentationSection docSection;
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
    public int compareTo(TraceLink o) {
        return Double.valueOf(this.matches).compareTo(Double.valueOf(o.getMatches()));
    }
}
