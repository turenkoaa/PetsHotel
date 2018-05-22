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

public class EntitiesFactory {

    public static Supplier<User> createNewExampleUser =
            () -> User.builder()
                .firstName("Сомин")
                .lastName("Игорь")
                .active(true)
                .userType(UserType.CUSTOMER)
                .email("somin@smth.com")
                .address("Новочеркасская")
                .build();

    public static Supplier<Pet> createNewExamplePet =
            () -> Pet.builder()
                .petType(PetType.CAT)
                .name("Вася")
                .age(2)
                .passport("12234")
                .build();

    public static Supplier<RequestDto> createNewExampleRequestDto =
        () -> RequestDto.builder()
            .startDate(LocalDate.of(2018, 06, 02))
            .endDate(LocalDate.of(2018, 07, 13))
            .cost(2000)
            .build();

}
