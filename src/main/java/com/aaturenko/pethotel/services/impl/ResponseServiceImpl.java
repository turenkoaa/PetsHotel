package com.aaturenko.pethotel.services.impl;

import com.aaturenko.pethotel.dto.ResponseDto;
import com.aaturenko.pethotel.enums.ResponseStatus;
import com.aaturenko.pethotel.exceptions.EntityNotFoundException;
import com.aaturenko.pethotel.models.Request;
import com.aaturenko.pethotel.models.Response;
import com.aaturenko.pethotel.models.User;
import com.aaturenko.pethotel.repositories.ResponseRepository;
import com.aaturenko.pethotel.services.RequestService;
import com.aaturenko.pethotel.services.ResponseService;
import com.aaturenko.pethotel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.BiFunction;

import static java.util.stream.Collectors.toList;

@Service
public class ResponseServiceImpl implements ResponseService {

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @Override
    public Response createResponse(ResponseDto responseDto) {
        return responseRepository.save(validateAndCreateResponse(responseDto));
    }

    private Response validateAndCreateResponse(ResponseDto responseDto) {
        Request request = requestService.findRequestById(responseDto.getRequestId());
        User user = userService.findUserById(responseDto.getAuthorId());
        return new Response()
                .setRequest(request)
                .setUser(user)
                .setStatus(ResponseStatus.PROPOSED)
                .setDetails(responseDto.getDetails());
    }

    @Override
    public Response findResponseById(long id) {
        return responseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityNotFoundException.Entity.Response, id));
    }

    @Override
    public List<Response> findAllByUserId(long userId, int page, int size) {
        return responseRepository.findAllByUser_Id(userId, PageRequest.of(page, size)).getContent();
    }

    @Override
    public List<Response> findAllResponsesByRequestId(long requestId, int page, int size) {
        return responseRepository.findAllByRequest_Id(requestId, PageRequest.of(page, size)).getContent();
    }

    @Override
    @Transactional
    public Response acceptResponseById(long responseId) {
        Response response = changeStatus.apply(responseId, ResponseStatus.ACCEPTED);
        long requestId = response.getRequest().getId();

        requestService.solvedRequestById(requestId);

        rejectResponsesByIds(
                responseRepository.findAllByRequest_Id(requestId)
                        .stream()
                        .map(Response::getId)
                        .filter(id -> !id.equals(responseId))
                        .collect(toList())
        );

        return response;
    }

    @Override
    @Transactional
    public void deleteResponseById(long responseId) {
        Response response = findResponseById(responseId);

        if (ResponseStatus.ACCEPTED.equals(response.getStatus())) {
            long requestId = response.getRequest().getId();
            requestService.setNewStatusForRequestById(requestId);
            rejectAllResponsesForRequestId(requestId);
        }

        responseRepository.delete(response);
    }

    @Override
    @Transactional
    public void rejectAllResponsesForRequestId(long requestId) {
        rejectResponsesByIds(
                responseRepository.findAllByRequest_Id(requestId)
                        .stream()
                        .map(Response::getId)
                        .collect(toList())
        );
    }

    @Override
    @Transactional
    public Response rejectResponseById(long responseId) {
        return changeStatus.apply(responseId, ResponseStatus.REJECTED);
    }

    private void rejectResponsesByIds(List<Long> ids) {
        responseRepository.updateResponsesStatus(ResponseStatus.REJECTED, ids);
    }

    private BiFunction<Long, ResponseStatus, Response> changeStatus =
            (id, status) -> responseRepository.save(
                    findResponseById(id).setStatus(status)
            );
}
