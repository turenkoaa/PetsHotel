package com.aaturenko.pethotel.entities;

import com.aaturenko.pethotel.dto.PetDto;
import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.exceptions.EntityNotFoundException;
import com.aaturenko.pethotel.repositories.PetRepository;
import com.aaturenko.pethotel.repositories.RequestRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = true)
public class Owner extends User {

    private List<Pet> pets;
    private List<Request> requests;

    private Owner(long id, String firstName, String lastName, String email, Boolean active, String address, List<Pet> pets, List<Request> requests) {
        super(id, firstName, lastName, email, active, address);
        this.pets = pets;
        this.requests = requests;
    }

    public Request addRequest(RequestDto requestDto){
        Request request = Request.newRequest(requestDto, this);
        requests.add(request);
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

    public List<Pet> getPets() {
        if (pets == null)
            pets = Pet.findAllByUser(this);
        return pets;
    }

    public List<Request> getRequests() {
        if (requests == null)
            requests = Request.findAllByUser(this);
        return requests;
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

        public void setResponses(List<Request> requests) {
            this.requests = requests;
        }
    }
}
