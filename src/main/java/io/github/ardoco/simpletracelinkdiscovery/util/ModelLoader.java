/* Licensed under MIT 2022. */
package io.github.ardoco.simpletracelinkdiscovery.util;

import edu.kit.kastel.mcse.ardoco.core.api.data.model.IModelInstance;
import edu.kit.kastel.mcse.ardoco.core.model.IModelConnector;
import io.github.ardoco.simpletracelinkdiscovery.entity.ModelEntity;
import org.eclipse.collections.api.list.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class ModelLoader {

    IModelConnector modelConnector;

    public ModelLoader(IModelConnector modelConnector) {
        this.modelConnector = modelConnector;
    }

    public List<ModelEntity> modelEntityList() {
        ImmutableList<IModelInstance> instances = modelConnector.getInstances();
        List<ModelEntity> entities = new ArrayList<>();

        for (IModelInstance modelInstance : instances) {
            List<String> nameParts = modelInstance.getNameParts().toList();
            nameParts.remove(nameParts.size() - 1);
            ModelEntity entity = new ModelEntity(modelInstance.getFullName(), nameParts, modelInstance.getUid());
            entities.add(entity);
        }

        return entities;
    }
}
