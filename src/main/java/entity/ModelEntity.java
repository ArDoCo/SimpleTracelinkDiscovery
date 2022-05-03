package entity;
import java.util.List;

public class ModelEntity {
    private String name;
    private String id;
    private List<String> nameParts;
    public ModelEntity(String name, List<String> nameParts, String id) {
        this.name = name;
        this.id = id;
        this.nameParts =  nameParts;
    }
    public String getName() {
        return name;
    }

    public List<String> getNameParts() {
        return nameParts;
    }

    public String getId() {
        return id;
    }
}
