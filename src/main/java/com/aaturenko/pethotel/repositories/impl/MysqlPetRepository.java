package com.aaturenko.pethotel.repositories.impl;

import com.aaturenko.pethotel.dao.DataMapperRegistry;
import com.aaturenko.pethotel.dao.mapper.DataMapper;
import com.aaturenko.pethotel.dao.mapper.PetMapper;
import com.aaturenko.pethotel.entities.Pet;
import com.aaturenko.pethotel.entities.User;
import com.aaturenko.pethotel.repositories.PetRepository;
import com.sun.org.apache.bcel.internal.generic.DMUL;

import java.util.List;

public class MysqlPetRepository implements PetRepository {


    private PetMapper mapper; // DataMapperRegistry.getMapper(Pet.class);

    @Override
    public Pet save(Pet pet) {
        return (Pet) mapper.save(pet);
    }

    @Override
    public List<Pet> findAllByUser(User user) {
        return mapper.findAllByUser(user);
    }

    @Override
    public DataMapper getDataMapper() {
        return mapper;
    }
}
