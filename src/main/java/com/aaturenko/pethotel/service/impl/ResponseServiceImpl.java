package com.aaturenko.pethotel.service.impl;

import com.aaturenko.pethotel.dto.ResponseDto;
import com.aaturenko.pethotel.exceptions.EntityNotFoundException;
import com.aaturenko.pethotel.exceptions.EntityNotFoundException.Entity;
import com.aaturenko.pethotel.model.Request;
import com.aaturenko.pethotel.model.Response;
import com.aaturenko.pethotel.model.User;
import com.aaturenko.pethotel.repo.ResponseRepository;
import com.aaturenko.pethotel.service.RequestService;
import com.aaturenko.pethotel.service.ResponseService;
import com.aaturenko.pethotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.aaturenko.pethotel.enums.ResponseStatus.PROPOSED;

@Service
public class ResponseServiceImpl implements ResponseService {

    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private RequestService requestService;
    @Autowired
    private UserService userService;

    @Override
    public List<Response> findForRequest(long requestId) {
        //todo request to answer!!
        return responseRepository.findAllByRequest_Id(requestId);
    }

    @Override
    public List<Response> findForUser(long authorId) {
        return responseRepository.findAllByUser_Id(authorId);
    }

    @Override
    public Response findById(long id) {
        return responseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Entity.Response, id));
    }

    @Override
    public Response save(ResponseDto responseDto) {
        Request request = requestService.findById(responseDto.getRequestId());
        User user = userService.findById(responseDto.getAuthorId());
        Response response = Response.builder()
                .id(0)
                .request(request)
                .details(responseDto.getDetails())
                .status(PROPOSED)
                .user(user)
                .cost(responseDto.getCost())
                .build();

        return responseRepository.save(response);
    }

    @Override
    public void deleteById(long id) {
        responseRepository.deleteById(id);
    }
}
