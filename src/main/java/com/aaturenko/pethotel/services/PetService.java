package com.aaturenko.pethotel.services;

import com.aaturenko.pethotel.models.Pet;

import java.util.List;

public interface PetService {
    Pet saveOrUpdatePet(Pet pet);
    Pet findPetById(long id);
    List<Pet> findAllById(List<Long> ids);
    List<Pet> findPetByOwnerId(long ownerId);
    void deletePetById(long id);
}
