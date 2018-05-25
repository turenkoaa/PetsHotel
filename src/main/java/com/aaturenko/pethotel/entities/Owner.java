package com.aaturenko.pethotel.entities;

import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.exceptions.EntityNotFoundException;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class Owner extends User{

    private List<Pet> pets;
    private List<Request> requests;

    public Owner(long id, String firstName, String lastName, String email, Boolean active, String address, List<Pet> pets, List<Request> requests) {
        super(id, firstName, lastName, email, active, address);
        this.pets = pets;
        this.requests = requests;
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

    public void addRequest(RequestDto requestDto){
        if (requestDto.getStartDate().compareTo(requestDto.getEndDate()) > 0)
            throw new IllegalArgumentException("Start date must be less then end date.");

        requests.add(Request.newRequest(requestDto, this));
    }

    @Override
    public void block() {
        throw new UnsupportedOperationException();
    }

//    public void deleteRequest(Long requestId){
//        requests.remove(
//                findRequest(requestId).orElseThrow(() -> new EntityNotFoundException(EntityNotFoundException.Entity.Request, requestId))
//        );
//    }
//
//    private Optional<Request> findRequest(Long requestId) {
//
//        for (Request request: requests) {
//            if (requestId.equals(request.getId()))
//                return Optional.of(request);
//        }
//
//        return Optional.empty();
//    }

}
