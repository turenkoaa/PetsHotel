package com.aaturenko.pethotel.old.services;

import com.aaturenko.pethotel.old.models.Pet;

import java.util.List;

public interface PetService {
    Pet saveOrUpdatePet(Pet pet);
    Pet findPetById(long id);
    List<Pet> findAllById(List<Long> ids);
    List<Pet> findAllById(List<Long> ids, int page, int size);
    List<Pet> findPetsByOwnerId(long ownerId, int page, int size);
    void deletePetById(long id);
}