package com.aaturenko.pethotel.repositories;

import com.aaturenko.pethotel.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Query("update User u set u.active=?2 where u.id=?1")
    void updateUserActiveStatus(long userId, boolean active);
}
