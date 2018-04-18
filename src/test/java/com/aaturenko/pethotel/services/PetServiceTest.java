package com.aaturenko.pethotel.services;

import com.aaturenko.pethotel.enums.UserType;
import com.aaturenko.pethotel.exceptions.EntityNotFoundException;
import com.aaturenko.pethotel.exceptions.UniqueNameException;
import com.aaturenko.pethotel.models.Pet;
import com.aaturenko.pethotel.models.User;
import com.aaturenko.pethotel.repositories.PetRepository;
import com.aaturenko.pethotel.repositories.UserRepository;
import com.aaturenko.pethotel.services.utils.InitEntities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.aaturenko.pethotel.services.utils.InitEntities.createNewExamplePet;
import static com.aaturenko.pethotel.services.utils.InitEntities.createNewExampleUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PetServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    private Pet savedPet;
    private User savedUser;

    @Before
    public void initDb() {
        savedUser = userService.saveOrUpdateUser(createNewExampleUser.get());
        savedPet = petService.saveOrUpdatePet(
                createNewExamplePet.get()
                        .setOwner(savedUser)
        );
    }

    @After
    public void deleteDb() {
        userService.deleteUserById(savedPet.getOwner().getId());
    }

    @Test
    public void petSuccessfullySavedUpdatedAndDeleted () {
        //check saving
        Long savedPetId = savedPet.getId();
        assertTrue(savedPetId > 0);
        assertEquals(savedPet.getOwner().getEmail(), savedUser.getEmail());

        //updating and finding
        savedPet.setName("Петя");
        petService.saveOrUpdatePet(savedPet);
        assertEquals(petService.findPetById(savedPetId).getName(), "Петя");
    }

    @Test(expected = EntityNotFoundException.class)
    public void petNotFoundById () {
        petService.findPetById(9999999999999L);
    }

    @Test
    public void petNotFoundByOwner () {
        assertTrue(petService.findPetByOwnerId(9999999999999L).isEmpty());
    }

    @Test(expected = UniqueNameException.class)
    public void petWithExistingPasswordFailed () {
        petService.saveOrUpdatePet(createNewExamplePet.get());
    }
}
