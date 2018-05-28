package com.aaturenko.pethotel.repositories;

import com.aaturenko.pethotel.dto.PetDto;
import com.aaturenko.pethotel.entities.Pet;
import com.aaturenko.pethotel.entities.User;

import java.util.List;

public interface PetRepository extends Repository{
    Pet save(Pet pet);
    List<Pet> findAllByUser(User user);
}
