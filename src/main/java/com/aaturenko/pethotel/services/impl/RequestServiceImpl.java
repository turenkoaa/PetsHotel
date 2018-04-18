package com.aaturenko.pethotel.services.impl;

import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.enums.RequestStatus;
import com.aaturenko.pethotel.enums.ResponseStatus;
import com.aaturenko.pethotel.exceptions.EntityNotFoundException;
import com.aaturenko.pethotel.models.Pet;
import com.aaturenko.pethotel.models.Request;
import com.aaturenko.pethotel.models.Response;
import com.aaturenko.pethotel.models.User;
import com.aaturenko.pethotel.repositories.RequestRepository;
import com.aaturenko.pethotel.services.PetService;
import com.aaturenko.pethotel.services.RequestService;
import com.aaturenko.pethotel.services.ResponseService;
import com.sun.javafx.geom.transform.Identity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    private final PetService petService;
//    private final ResponseService responseService;

    @Override
    public Request createRequest(RequestDto request) {
        return requestRepository.save(validateAndCreateRequest(request));
    }

    @Override
    public Request findRequestById(long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityNotFoundException.Entity.Request, id));
    }

    private Request validateAndCreateRequest(RequestDto requestDto) {
        if (requestDto.getStartDate().compareTo(requestDto.getEndDate()) > 0)
            throw new IllegalArgumentException("Start date must be less then end date.");

        List<Pet> pets = petService.findAllById(requestDto.getPetsIds());
        if (pets.isEmpty()) throw new IllegalArgumentException("Pets list must not be empty.");

        User owner = pets.get(0).getOwner();
        for (Pet pet : pets) {
            if (!owner.equals(pet.getOwner()))
                throw new IllegalArgumentException("Pets list must not consists of pets of different owners.");
        }

        return mapFromDto.apply(requestDto, pets);
    }

    @Override
    public List<Request> findAllNewRequests(int page, int size) {
        return requestRepository.findAllByStatusAndEndDateGreaterThanOrderByStartDate(
                RequestStatus.NEW,
                LocalDate.now(),
                //todo add desc
                PageRequest.of(page, size)
        ).getContent();
    }

    @Override
    public List<Request> findAllRequestsByAuthor(long userId, int page, int size) {
        return requestRepository.findAllByUser_IdOrderByStartDate(
                userId,
                //todo add desc
                PageRequest.of(page, size)
        ).getContent();
    }

    @Override
    public void deleteRequestById(long id) {
        throw new UnsupportedOperationException("It is not able to delete request else.");
    }

    @Override
    @Transactional
    public Request solvedRequestById(long requestId) {
        return changeStatus.apply(requestId, RequestStatus.SOLVED);
    }

    @Override
    public Request setNewStatusForRequestById(long requestId) {
        return changeStatus.apply(requestId, RequestStatus.SOLVED);
    }

    @Override
    @Transactional
    public Request anulledRequestByIdAndRejectItsResponses(long requestId) {
//        responseService.rejectAllResponsesForRequestId(requestId);
        return changeStatus.apply(requestId, RequestStatus.ANULLED);
    }

    private BiFunction<Long, RequestStatus, Request> changeStatus =
            (id, status) -> requestRepository.save(findRequestById(id).setStatus(status));


    private BiFunction<RequestDto, List<Pet>, Request> mapFromDto = (dto, pets) -> {
        Request request = new Request()
                .setStartDate(dto.getStartDate())
                .setEndDate(dto.getEndDate())
                .setUser(pets.get(0).getOwner())
                .setStatus(RequestStatus.NEW);
        request.getPets().addAll(pets);
        return request;
    };
}
