package com.aaturenko.pethotel.services;

import com.aaturenko.pethotel.dto.PetDto;
import com.aaturenko.pethotel.old.entities.Pet;
import com.aaturenko.pethotel.old.entities.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.aaturenko.pethotel.enums.PetType.CAT;
import static com.aaturenko.pethotel.services.utils.EntitiesFactory.createNewExamplePet;
import static com.aaturenko.pethotel.services.utils.EntitiesFactory.createNewExampleUser;
import static junit.framework.TestCase.assertNull;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class PetServiceTest extends DatabaseTest {

    private Pet savedPet;
    private User savedUser;

    @Before
    public void initDb() {
        savedUser = (User) createNewExampleUser.get().save();
        PetDto petDto = new PetDto()
                .setAge(9)
                .setName("test")
                .setPassport("123456789")
                .setPetType(CAT.toString())
                .setUserId(savedUser.getId());
        savedPet = Pet.newPet(petDto, savedUser);
        savedPet.setUser(savedUser);
    }

    @After
    public void deleteDb() {
        savedUser.delete();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void petSuccessfullySavedUpdatedAndDeleted () {
        //check saving
        Long savedPetId = savedPet.getId();
        assertTrue(savedPetId > 0);
        assertEquals(savedPet.getUser().getEmail(), savedUser.getEmail());

        //updating and finding
        savedPet.setName("Петя");
        savedPet.save();
        assertEquals(Pet.find(savedPetId).getName(), "Петя");
    }

    @Test
    public void petNotFoundById () {
        assertNull(Pet.find(9999999999999L));
    }

    @Test
    public void petSuccessFoundByOwner () {
        assertThat(Pet.findAllByUser(savedUser), hasSize(1));
    }

    @Test(expected = Exception.class)
    public void petWithExistingPasswordFailed () {
        createNewExamplePet.get().save();
    }
}
