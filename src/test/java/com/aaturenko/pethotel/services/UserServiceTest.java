package com.aaturenko.pethotel.services;

import com.aaturenko.pethotel.entities.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.aaturenko.pethotel.services.utils.EntitiesFactory.createNewExampleUser;
import static org.junit.Assert.*;

public class UserServiceTest extends DatabaseTest {


    private User savedUser;

    @Before
    public void initDb() {
        savedUser = (User) createNewExampleUser.get().save();
    }

    @After
    public void deleteDb() {
        savedUser.delete();
    }

    @Test
    public void userSuccessfullySavedUpdatedAndDeleted () {
        //check saving
        Long savedUserId = savedUser.getId();
        assertTrue(savedUserId > 0);

        //updating and finding
        String address = "something else";
        savedUser.setAddress(address);
        savedUser.save();
        assertEquals(User.find(savedUserId).getAddress(), address);
    }

    @Test
    public void userNotFoundById () {
        assertNull(User.find(9999999999999L));
    }

    @Test(expected = RuntimeException.class)
    public void userWithExistingEmailFailed () {
        createNewExampleUser.get().save();
    }


}
