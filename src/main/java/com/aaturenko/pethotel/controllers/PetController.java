package com.aaturenko.pethotel.controllers;

import com.aaturenko.pethotel.models.Pet;
import com.aaturenko.pethotel.models.User;
import com.aaturenko.pethotel.services.PetService;
import com.aaturenko.pethotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    @GetMapping("/all-by-ids")
    public ResponseEntity<List<Pet>> findPets(
            @RequestBody List<Long> ids,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(petService.findAllById(ids, page, size));
    }

    @GetMapping("/all-by-owner/{ownerId}")
    public ResponseEntity<List<Pet>> findPets(
            @PathVariable long ownerId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(petService.findPetsByOwnerId(ownerId, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> findPet(
            @PathVariable long id) {
        return ResponseEntity.ok(petService.findPetById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Pet> savePet(@RequestBody Pet pet) {
        return ResponseEntity.ok(petService.saveOrUpdatePet(pet));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deletePet(@PathVariable long id) {
        petService.deletePetById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
