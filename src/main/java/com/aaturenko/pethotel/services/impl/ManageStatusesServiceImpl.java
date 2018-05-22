package com.aaturenko.pethotel.services.impl;

import com.aaturenko.pethotel.enums.RequestStatus;
import com.aaturenko.pethotel.enums.ResponseStatus;
import com.aaturenko.pethotel.enums.UserType;
import com.aaturenko.pethotel.exceptions.EntityNotFoundException;
import com.aaturenko.pethotel.models.Request;
import com.aaturenko.pethotel.models.Response;
import com.aaturenko.pethotel.models.User;
import com.aaturenko.pethotel.repositories.RequestRepository;
import com.aaturenko.pethotel.repositories.ResponseRepository;
import com.aaturenko.pethotel.services.ManageStatusesService;
import com.aaturenko.pethotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

import static com.aaturenko.pethotel.enums.ResponseStatus.REJECTED;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class ManageStatusesServiceImpl implements ManageStatusesService {

    private final RequestRepository requestRepository;
    private final ResponseRepository responseRepository;

    @Override
    @Transactional
    public void acceptResponse(Response response) {
        Request request = response.getRequest();

        changeRequestStatus(request, RequestStatus.SOLVED);

        List<Long> ids = responseRepository.findAllByRequest_Id(request.getId())
                .stream()
                .map(Response::getId)
                .filter(id -> !id.equals(response.getId()))
                .collect(toList());

        changeResponseStatus(response, ResponseStatus.ACCEPTED);
        responseRepository.updateResponsesStatus(REJECTED, ids);

    }

    @Override
    @Transactional
    public void manageStatusesForResponseRemoving(Response response) {
        validateBeforeResponseRejection(response);
    }

    @Override
    @Transactional
    public void manageStatusesForUserRemoving(long userId) {
        requestRepository.findAllByUser_Id(userId)
                .forEach(r -> updateResponsesStatusForRequest(r.getId(), REJECTED));
    }

    @Override
    @Transactional
    public void manageStatusesForBlockedUserResponses(long userId) {
        List<Long> ids = responseRepository.findAllByUser_Id(userId)
                .stream()
                .filter(r -> !REJECTED.equals(r.getStatus()))
                .peek(this::validateBeforeResponseRejection)
                .map(Response::getId)
                .collect(toList());
        if (!ids.isEmpty()) responseRepository.updateResponsesStatus(REJECTED, ids);
    }

    @Override
    @Transactional
    public void manageStatusesForBlockedUserRequests(long userId) {
        requestRepository.findAllByUser_Id(userId)
                .forEach(this::anulledRequestAndRejectItsResponses);
    }

    @Override
    @Transactional
    public void manageStatusesForRequestRemoving(Request request) {
        updateResponsesStatusForRequest(request.getId(), REJECTED);
    }

    @Override
    @Transactional
    public void rejectAllResponsesForRequest(Request request) {
        updateResponsesStatusForRequest(request.getId(), REJECTED);
        changeRequestStatus(request, RequestStatus.NEW);
    }


    @Override
    @Transactional
    public void anulledRequestAndRejectItsResponses(Request request) {
        updateResponsesStatusForRequest(request.getId(), REJECTED);
        changeRequestStatus(request, RequestStatus.ANULLED);
    }


    private void validateBeforeResponseRejection(Response response) {
        if (ResponseStatus.ACCEPTED.equals(response.getStatus())) {
            Request request = response.getRequest();
            changeRequestStatus(request, RequestStatus.NEW);
            updateResponsesStatusForRequest(request.getId(), ResponseStatus.PROPOSED);
        }
    }

    private Response changeResponseStatus(Response response, ResponseStatus status) {
        return status.equals(response.getStatus())
                ? response
                : responseRepository.save(response.setStatus(status));
    }

    private void changeRequestStatus(Request request, RequestStatus status) {
        if (!status.equals(request.getStatus())) {
            requestRepository.save(request.setStatus(status));
        }
    }

    private void updateResponsesStatusForRequest(long requestId, ResponseStatus status) {
        List<Long> ids = responseRepository.findAllByRequest_Id(requestId, PageRequest.of(0, 10))
                .stream()
                .filter(r -> !status.equals(r.getStatus()))
                .map(Response::getId)
                .collect(toList());
        if (!ids.isEmpty()) responseRepository.updateResponsesStatus(status, ids);
    }

}
