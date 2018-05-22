package com.aaturenko.pethotel.services;

import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.enums.RequestStatus;
import com.aaturenko.pethotel.models.Pet;
import com.aaturenko.pethotel.models.Request;
import com.aaturenko.pethotel.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.aaturenko.pethotel.services.utils.EntitiesFactory.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class RequestServiceTest extends DatabaseTest{

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @Autowired
    private RequestService requestService;

    private Pet savedPet_1;
    private User savedUser_1;
    private Pet savedPet_2;

    @Before
    public void initDb() {
        savedUser_1 = userService.saveOrUpdateUser(createNewExampleUser.get());
        savedPet_1 = petService.saveOrUpdatePet(
                createNewExamplePet.get()
                        .setOwner(savedUser_1)
        );

        User savedUser_2 = userService.saveOrUpdateUser(createNewExampleUser.get().setEmail("new@mail.ru"));
        savedPet_2 = petService.saveOrUpdatePet(
                createNewExamplePet.get()
                        .setPassport("999999")
                        .setOwner(savedUser_2)
        );
    }

    @After
    public void deleteDb() {
        userService.deleteUserById(savedPet_1.getOwner().getId());
        userService.deleteUserById(savedPet_2.getOwner().getId());
    }

    @Test
    public void savedRequestSuccessfully() {
        RequestDto requestDto = createNewExampleRequestDto.get()
                .setPetsIds(Collections.singletonList(savedPet_1.getId()));
        Request request = requestService.createRequest(requestDto);
        assertEquals(savedUser_1, request.getUser());
        assertThat(request.getPets(), hasSize(1));
        assertEquals(RequestStatus.NEW, request.getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void savedRequestFailedWithEmptyListOfPets() {
        requestService.createRequest(
                createNewExampleRequestDto.get()
                        .setPetsIds(Collections.emptyList()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void savedRequestFailedWithIllegalDate() {
        requestService.createRequest(
                createNewExampleRequestDto.get()
                        .setStartDate(LocalDate.of(2019, 10, 19)))
                        .setEndDate(LocalDate.of(2019, 10, 18));
    }

    @Test(expected = IllegalArgumentException.class)
    public void savedRequestFailedWithPets() {
        requestService.createRequest(
                createNewExampleRequestDto.get()
                        .setPetsIds(Arrays.asList(savedPet_1.getId(), savedPet_2.getId())));
    }


    @Test
    public void findAllNewRequestsSuccessfully() {
        List<Request> requests = requestService.findAllNewRequests(0, 10);
        assertThat(requests, hasSize(2));
    }

    @Test
    public void findAllByAuthorSuccessfully() {
        List<Request> requests = requestService.findAllRequestsByAuthor(1, 0,10);
        assertThat(requests, hasSize(1));
        assertEquals(1L, requests.get(0).getId());
    }
}
