package com.aaturenko.pethotel.repositories;

import com.aaturenko.pethotel.dao.mapper.DataMapper;
import com.aaturenko.pethotel.entities.Entity;

public interface Repository {
    DataMapper getDataMapper();

    default Entity findById(long entityId) {
        return getDataMapper().findById(entityId);
    }

    default Entity update(Entity entity) {
        return getDataMapper().update(entity);
    }
}
