package entity;

import java.util.ArrayList;
import java.util.List;

public class ModelEntity {
    private String name;
    private List<String> nameParts;
    public ModelEntity(String name, List<String> nameParts) {
        this.name = name;
        this.nameParts =  nameParts;
    }
    public String getName() {
        return name;
    }

    public List<String> getNameParts() {
        return nameParts;
    }

}
