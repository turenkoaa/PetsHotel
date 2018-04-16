package com.aaturenko.pethotel.services.utils;

import com.aaturenko.pethotel.enums.PetType;
import com.aaturenko.pethotel.enums.UserType;
import com.aaturenko.pethotel.models.Pet;
import com.aaturenko.pethotel.models.User;

public class InitEntities {

    public static User createNewExampleUser() {
        return new User()
                .setFirstName("Сомин")
                .setLastName("Игорь")
                .setActive(true)
                .setUserType(UserType.OWNER)
                .setEmail("somin@smth.com")
                .setAddress("Новочеркасская");
    }

    public static Pet createNewExamplePet() {
        return new Pet()
                .setPetType(PetType.CAT)
                .setName("Вася")
                .setAge(2)
                .setPassport("12234");
    }
}
