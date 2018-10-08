package com.aaturenko.pethotel.services;


import com.aaturenko.pethotel.dto.PetDto;
import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.old.entities.Pet;
import com.aaturenko.pethotel.old.entities.Request;
import com.aaturenko.pethotel.old.entities.User;
import com.aaturenko.pethotel.enums.RequestStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static com.aaturenko.pethotel.enums.PetType.CAT;
import static com.aaturenko.pethotel.services.utils.EntitiesFactory.createNewExampleRequestDto;
import static com.aaturenko.pethotel.services.utils.EntitiesFactory.createNewExampleUser;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class RequestServiceTest extends DatabaseTest {

    private Pet savedPet_1;
    private User savedUser_1;
    private User savedUser_2;
    private Pet savedPet_2;

    @Before
    public void initDb() {
        savedUser_1 = (User) createNewExampleUser.get().save();
        PetDto petDto = new PetDto()
                .setAge(9)
                .setName("test")
                .setPassport("123456789")
                .setPetType(CAT.toString())
                .setUserId(savedUser_1.getId());
        savedPet_1 = Pet.newPet(petDto, savedUser_1);
        savedPet_1.setUser(savedUser_1);


        User user = createNewExampleUser.get();
        user.setEmail("new@mail.ru");
        savedUser_2 = (User) user.save();
        petDto = new PetDto()
                .setAge(9)
                .setName("test")
                .setPassport("999999")
                .setPetType(CAT.toString())
                .setUserId(savedUser_2.getId());
        savedPet_2 = Pet.newPet(petDto, savedUser_2);
        savedPet_2.setUser(savedUser_2);

    }

    @After
    public void deleteDb() {
        savedUser_1.delete();
        savedUser_2.delete();
    }

    @Test
    public void savedRequestSuccessfully() {
        RequestDto requestDto = createNewExampleRequestDto.get()
                .setPetId(savedPet_1.getId());
        Request request = Request.newRequest(requestDto, savedUser_1);
        assertEquals(savedUser_1.getEmail(), request.getUser().getEmail());
        assertEquals(request.getPet().getName(), savedPet_1.getName());
        assertEquals(RequestStatus.NEW, request.getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void savedRequestFailedWithIllegalDate() {
        RequestDto requestDto = createNewExampleRequestDto.get()
                .setPetId(savedPet_1.getId())
                .setStartDate(LocalDate.of(2019, 10, 19))
                .setEndDate(LocalDate.of(2019, 10, 18));
        Request.newRequest(requestDto, savedUser_1);
    }

    @Test
    public void findAllNewRequestsSuccessfully() {
        List<Request> requests = Request.findNewRequests(1L);
        assertThat(requests, hasSize(1));
    }

    @Test
    public void findAllByAuthorSuccessfully() {
        List<Request> requests = Request.findAllByUser(User.find(2L));
        assertThat(requests, hasSize(1));
        assertEquals(2L, requests.get(0).getId());
    }
}
