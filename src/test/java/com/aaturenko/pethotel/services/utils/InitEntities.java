package com.aaturenko.pethotel.services.utils;

import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.enums.PetType;
import com.aaturenko.pethotel.enums.UserType;
import com.aaturenko.pethotel.models.Pet;
import com.aaturenko.pethotel.models.Request;
import com.aaturenko.pethotel.models.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.function.Supplier;

public class InitEntities {

    public static Supplier<User> createNewExampleUser =
            () -> new User()
                .setFirstName("Сомин")
                .setLastName("Игорь")
                .setActive(true)
                .setUserType(UserType.OWNER)
                .setEmail("somin@smth.com")
                .setAddress("Новочеркасская");

    public static Supplier<Pet> createNewExamplePet =
            () -> new Pet()
                .setPetType(PetType.CAT)
                .setName("Вася")
                .setAge(2)
                .setPassport("12234");

    public static Supplier<RequestDto> createNewExampleRequestDto =
        () -> new RequestDto()
            .setStartDate(LocalDate.of(2018, 06, 02))
            .setEndDate(LocalDate.of(2018, 07, 13))
            .setCost(2000);

}
