package com.aaturenko.pethotel.services;

import com.aaturenko.pethotel.enums.UserType;
import com.aaturenko.pethotel.exceptions.EntityNotFoundException;
import com.aaturenko.pethotel.exceptions.UniqueNameException;
import com.aaturenko.pethotel.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.aaturenko.pethotel.services.utils.EntitiesFactory.createNewExampleUser;
import static org.junit.Assert.*;

public class UserServiceTest extends DatabaseTest {

    @Autowired
    private UserService userService;

    private User savedUser;

    @Before
    public void initDb() {
        savedUser = userService.saveOrUpdateUser(createNewExampleUser.get());
    }

    @After
    public void deleteDb() {
        userService.deleteUserById(savedUser.getId());
    }

    @Test
    public void userSuccessfullySavedUpdatedAndDeleted () {
        //check saving
        Long savedUserId = savedUser.getId();
        assertTrue(savedUserId > 0);
        assertEquals(savedUser.getUserType(), UserType.CUSTOMER);

        //updating and finding
        String address = "something else";
        savedUser.setAddress(address);
        userService.saveOrUpdateUser(savedUser);
        assertEquals(userService.findUserById(savedUserId).getAddress(), address);
    }

    @Test(expected = EntityNotFoundException.class)
    public void userNotFoundById () {
        userService.findUserById(9999999999999L);
    }

    @Test(expected = EntityNotFoundException.class)
    public void userNotFoundByEmail () {
        userService.findUserByEmail("email_non-valid");
    }

    @Test(expected = UniqueNameException.class)
    public void userWithExistingEmailFailed () {
        userService.saveOrUpdateUser(createNewExampleUser.get());
    }


}
