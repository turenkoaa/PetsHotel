package com.aaturenko.pethotel.repo;

import com.aaturenko.pethotel.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<List<Pet>> findAllByUser_Id(long userId);
}
