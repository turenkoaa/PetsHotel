package com.aaturenko.pethotel.services.utils;

import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.old.entities.Owner;
import com.aaturenko.pethotel.old.entities.Pet;
import com.aaturenko.pethotel.old.entities.User;
import com.aaturenko.pethotel.enums.PetType;

import java.time.LocalDate;
import java.util.function.Supplier;

public class EntitiesFactory {

    public static Supplier<User> createNewExampleUser =
            () -> Owner.builder()
                .firstName("Сомин")
                .lastName("Игорь")
                .active(true)
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
