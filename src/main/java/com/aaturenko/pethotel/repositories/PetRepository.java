package com.aaturenko.pethotel.repositories;

import com.aaturenko.pethotel.models.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Page<Pet> findAllByOwner_Id(long id, Pageable pageable);
    Page<Pet> findAllById(List<Long> ids, Pageable pageable);
    Optional<Pet> findByPassport(String passport);
}
