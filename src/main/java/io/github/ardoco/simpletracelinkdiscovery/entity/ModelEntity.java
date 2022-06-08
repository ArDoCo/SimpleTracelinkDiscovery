/* Licensed under MIT 2022. */
package io.github.ardoco.simpletracelinkdiscovery.entity;

import java.util.List;

public class ModelEntity {
    private final String name;
    private final String id;
    private final List<String> nameParts;

    public ModelEntity(String name, List<String> nameParts, String id) {
        this.name = name;
        this.id = id;
        this.nameParts = nameParts;
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
