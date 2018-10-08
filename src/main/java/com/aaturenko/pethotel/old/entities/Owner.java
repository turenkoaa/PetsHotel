package com.aaturenko.pethotel.old.entities;

import com.aaturenko.pethotel.old.dao.DataMapperRegistry;
import com.aaturenko.pethotel.old.dao.mapper.OwnerMapper;
import com.aaturenko.pethotel.dto.PetDto;
import com.aaturenko.pethotel.dto.RequestDto;
import lombok.EqualsAndHashCode;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
public class Owner extends User {

    private List<Pet> pets;
    private List<Request> requests;

    private static OwnerMapper userMapper = (OwnerMapper) DataMapperRegistry.getMapper(Owner.class);

    private Owner(long id, String firstName, String lastName, String email, Boolean active, String address, List<Pet> pets, List<Request> requests) {
        super(id, firstName, lastName, email, active, address);
        this.pets = pets;
        this.requests = requests;
    }

    public static Owner findByRequest(Request request) {
        return userMapper.findByRequest(request);
    }

    public Request addRequest(RequestDto requestDto){
        Request request = Request.newRequest(requestDto, this);
        requests().add(request);
        return request;
    }

    public Pet addPet(PetDto petDto) {
        Pet pet = Pet.newPet(petDto, this);
        pets.add(pet);
        return pet;
    }

    @Override
    public void block() {
        throw new UnsupportedOperationException();
    }

    public List<Pet> pets() {
        if (pets == null)
            pets = Pet.findAllByUser(this);
        return pets;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public List<Request> requests() {
        if (requests == null)
            requests = Request.findAllByUser(this);
        return requests;
    }

    public void confirmPayment(Request request) {
        request.paidConfirmed();
    }

    public static Owner find(long id) {
        return (Owner) userMapper.findById(id);
    }

    public static Owner findByEmail(String email) {
        return (Owner) userMapper.findByEmail(email);
    }

    public static OwnerBuilder builder() {
        return new OwnerBuilder();
    }

    public static class OwnerBuilder extends UserBuilder{

        private List<Request> requests;
        private List<Pet> pets;

        @Override
        public Owner build() {
            return new Owner(id, firstName, lastName, email, active, address, pets, requests);
        }

        public void setRequests(List<Request> requests) {
            this.requests = requests;
        }
    }
}
