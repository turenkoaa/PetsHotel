package com.aaturenko.pethotel.controllers;

import com.aaturenko.pethotel.dto.PetDto;
import com.aaturenko.pethotel.entities.Owner;
import com.aaturenko.pethotel.entities.Pet;
import com.aaturenko.pethotel.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pet")
public class PetController {

    @GetMapping("/all-by-owner/{ownerId}")
    public ResponseEntity<List<Pet>> findPets(@PathVariable long ownerId) {
        Owner owner = Owner.find(ownerId);
        return ResponseEntity.ok(owner.pets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> findPet(@PathVariable long id) {
        return ResponseEntity.ok(Pet.find(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Pet> savePet(@RequestBody PetDto petDto) {
        return ResponseEntity.ok(Pet.newPet(petDto, User.find(petDto.getUserId())));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deletePet(@PathVariable long id) {
        Pet.find(id).delete();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
