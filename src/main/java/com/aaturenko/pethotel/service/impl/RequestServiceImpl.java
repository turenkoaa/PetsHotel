package com.aaturenko.pethotel.service.impl;

import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.enums.RequestStatus;
import com.aaturenko.pethotel.exceptions.EntityNotFoundException;
import com.aaturenko.pethotel.model.Request;
import com.aaturenko.pethotel.service.PetService;
import com.aaturenko.pethotel.repo.RequestRepository;
import com.aaturenko.pethotel.service.RequestService;
import com.aaturenko.pethotel.service.UserService;
import com.aaturenko.pethotel.service.UpdateRequestAnsResponseStatusStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private PetService petService;
    @Autowired
    private UserService userService;
    @Autowired
    private UpdateRequestAnsResponseStatusStrategy statusStrategy;
    @Override
    public List<Request> findNewRequestsForUser(long userId) {
        return requestRepository.findAllByStatus(RequestStatus.NEW)
                .stream()
                .filter(r -> userId != r.getUser().getId())
                .distinct()
                .collect(toList());
    }

    @Override
    public List<Request> findAllRequestsOfUser(long userId) {
        return requestRepository.findAllByUser_Id(userId);
    }

    @Override
    public Request findById(long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityNotFoundException.Entity.Request, id));
    }

    @Override
    public Request saveRequest(RequestDto requestDto) {
        if (requestDto.getStartDate().compareTo(requestDto.getEndDate()) > 0)
            throw new IllegalArgumentException("Start date must be less then end date.");

        Request request = mapFromDto.apply(requestDto);
        request.setUser(userService.findById(requestDto.getUserId()));
        request.setPet(petService.findPetById(requestDto.getPetId()));
        return requestRepository.save(request);
    }

    private static Function<RequestDto, Request> mapFromDto = dto -> Request.builder()
                    .startDate(dto.getStartDate())
                    .endDate(dto.getEndDate())
                    .status(RequestStatus.NEW)
                    .cost(dto.getCost())
                    .paid(false)
                    .build();

    @Override
    public void deleteById(long id) {
        requestRepository.deleteById(id);
    }

    @Override
    public void annuledById(long id) {
        Request request = findById(id);
        statusStrategy.anulledRequest(request);
        requestRepository.save(request);
    }

    @Override
    public void rejectAllResponsesById(long id) {
        Request request = findById(id);
        statusStrategy.rejectResponses(request);
        requestRepository.save(request);
    }

    @Override
    @Transactional
    public void acceptResponse(long id, long responseId) {
        Request request = findById(id);
        statusStrategy.acceptResponse(request, responseId);
        requestRepository.save(request);
    }

    @Override
    public void confirmPayment(long id) {
        Request request = findById(id);
        request.setPaid(true);
        requestRepository.save(request);
    }
}
