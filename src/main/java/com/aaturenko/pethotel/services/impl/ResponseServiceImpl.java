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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ResponseServiceImpl implements ResponseService {

    private final ResponseRepository responseRepository;
    private final RequestService requestService;
    private final UserService userService;
    private final ManageStatusesServiceImpl statusesManageService;

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
                .setDetails(responseDto.getDetails())
                .setCost(responseDto.getCost());
    }

    @Override
    public Response findResponseById(long id) {
        return responseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityNotFoundException.Entity.Response, id));
    }

    @Override
    public List<Response> findAllResponsesByUserId(long userId, int page, int size) {
        return responseRepository.findAllByUser_Id(userId, PageRequest.of(page, size)).getContent();
    }

    @Override
    public List<Response> findAllResponsesByRequestId(long requestId, int page, int size) {
        return responseRepository.findAllByRequest_Id(requestId, PageRequest.of(page, size)).getContent();
    }

    @Override
    @Transactional
    public void deleteResponseById(long responseId) {
        Response response = findResponseById(responseId);
        statusesManageService.manageStatusesForResponseRemoving(response);
        // todo mark as deleted
        responseRepository.delete(response);
    }

}
