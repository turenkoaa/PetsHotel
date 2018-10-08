package com.aaturenko.pethotel.controllers;

import com.aaturenko.pethotel.dto.PetDto;
import com.aaturenko.pethotel.model.Pet;
import com.aaturenko.pethotel.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping("/all-by-owner/{ownerId}")
    public ResponseEntity<List<Pet>> findPets(@PathVariable long ownerId) {
        return ResponseEntity.ok(petService.findPetsOfUser(ownerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> findPet(@PathVariable long id) {
        return ResponseEntity.ok(petService.findPetById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Pet> savePet(@RequestBody PetDto petDto) {
        return ResponseEntity.ok(petService.savePet(petDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deletePet(@PathVariable long id) {
        petService.deletePetById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
