package com.aaturenko.pethotel.service;

import com.aaturenko.pethotel.dto.PetDto;
import com.aaturenko.pethotel.model.Pet;

import java.util.List;

public interface PetService {
    List<Pet> findPetsOfUser(long userId);
    Pet findPetById(long petId);
    void deletePetById(long petId);
    Pet savePet(PetDto petDto);
}
