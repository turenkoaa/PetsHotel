package com.aaturenko.pethotel.dao;

import com.aaturenko.pethotel.dao.mapper.*;
import com.aaturenko.pethotel.entities.*;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.util.HashMap;
import java.util.Map;

public class DataMapperRegistry {

    private static Map<Class<? extends Entity>, DataMapper> registry;

    public static DataMapper getMapper(Class<? extends Entity> clazz) {
        return registry.get(clazz);
    }

    public static void initialize(ComboPooledDataSource connection, final boolean useCache) {
        registry = new HashMap<>();
        registry.put(Owner.class, new OwnerMapper(connection, useCache));
        registry.put(Sitter.class, new SitterMapper(connection, useCache));
        registry.put(User.class, new SitterMapper(connection, useCache));
        registry.put(Request.class, new RequestMapper(connection, useCache));
        registry.put(Response.class, new ResponseMapper(connection, useCache));
        registry.put(Review.class, new ReviewMapper(connection, useCache));
        registry.put(Pet.class, new PetMapper(connection, useCache));
    }

    public static void clear() {
        registry.forEach((clazz, mapper) -> mapper.clearCache());
        registry.clear();
    }
}
