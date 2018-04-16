package com.aaturenko.pethotel.repositories;

import com.aaturenko.pethotel.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByOwner_Id(long id);
    Optional<Pet> findByPassport(String passport);
}
