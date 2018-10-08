package com.aaturenko.pethotel.service.impl;

import com.aaturenko.pethotel.dto.PetDto;
import com.aaturenko.pethotel.enums.PetType;
import com.aaturenko.pethotel.exceptions.EntityNotFoundException;
import com.aaturenko.pethotel.model.Pet;
import com.aaturenko.pethotel.model.User;
import com.aaturenko.pethotel.repo.PetRepository;
import com.aaturenko.pethotel.service.PetService;
import com.aaturenko.pethotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Pet> findPetsOfUser(long userId) {
        return petRepository.findAllByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Pets for user with id = " + userId + " was not found"));
    }

    @Override
    public Pet findPetById(long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new EntityNotFoundException(EntityNotFoundException.Entity.Pet, petId));
    }

    @Override
    public void deletePetById(long petId) {
        petRepository.deleteById(petId);
    }

    @Override
    public Pet savePet(PetDto petDto) {
        User user = userService.findById(petDto.getUserId());
        PetType petType = PetType.valueOf(petDto.getPetType());
        Pet pet = Pet.builder()
                .user(user)
                .age(petDto.getAge())
                .name(petDto.getName())
                .passport(petDto.getPassport())
                .petType(petType)
                .build();
        return petRepository.save(pet);
    }
}
