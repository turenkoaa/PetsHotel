package com.aaturenko.pethotel.services.impl;

import com.aaturenko.pethotel.enums.RequestStatus;
import com.aaturenko.pethotel.enums.ResponseStatus;
import com.aaturenko.pethotel.exceptions.EntityNotFoundException;
import com.aaturenko.pethotel.models.Request;
import com.aaturenko.pethotel.models.Response;
import com.aaturenko.pethotel.repositories.RequestRepository;
import com.aaturenko.pethotel.repositories.ResponseRepository;
import com.aaturenko.pethotel.services.ManageStatusesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class ManageStatusesServiceImpl implements ManageStatusesService {

    private final RequestRepository requestRepository;
    private final ResponseRepository responseRepository;

    @Override
    @Transactional
    public void acceptResponseById(long responseId) {
        Response response = changeResponseStatus(responseId, ResponseStatus.ACCEPTED);
        long requestId = response.getRequest().getId();

        changeRequestStatus(requestId, RequestStatus.SOLVED);

        List<Long> ids = responseRepository.findAllByRequest_Id(requestId)
                .stream()
                .map(Response::getId)
                .filter(id -> !id.equals(responseId))
                .collect(toList());
        responseRepository.updateResponsesStatus(ResponseStatus.REJECTED, ids);

    }

    @Override
    @Transactional
    public void manageStatusesForResponseRemoving(Response response) {
        if (ResponseStatus.ACCEPTED.equals(response.getStatus())) {
            long requestId = response.getRequest().getId();
            changeRequestStatus(requestId, RequestStatus.NEW);
            updateResponsesStatusForRequest(requestId, ResponseStatus.PROPOSED);
        }
    }

    @Override
    @Transactional
    public void manageStatusesForRequestRemoving(Request request) {
        updateResponsesStatusForRequest(request.getId(), ResponseStatus.REJECTED);
    }

    @Override
    @Transactional
    public void rejectAllResponsesForRequestId(long requestId) {
        updateResponsesStatusForRequest(requestId, ResponseStatus.REJECTED);
        changeRequestStatus(requestId, RequestStatus.NEW);
    }

    @Override
    @Transactional
    public void anulledRequestByIdAndRejectItsResponses(long requestId) {
        updateResponsesStatusForRequest(requestId, ResponseStatus.REJECTED);
        changeRequestStatus(requestId, RequestStatus.ANULLED);
    }

    private Response changeResponseStatus(long responseId, ResponseStatus status) {
        Response response = responseRepository.findById(responseId)
                .orElseThrow(() -> new EntityNotFoundException(EntityNotFoundException.Entity.Response, responseId));
        return status.equals(response.getStatus())
                ? response
                : responseRepository.save(response.setStatus(status));
    }

    private void changeRequestStatus(long requestId, RequestStatus status) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException(EntityNotFoundException.Entity.Request, requestId));
        if (!status.equals(request.getStatus())) {
            requestRepository.save(request.setStatus(status));
        }
    }

    private void updateResponsesStatusForRequest(long requestId, ResponseStatus status) {
        List<Long> ids = responseRepository.findAllByRequest_Id(requestId, PageRequest.of(0, 10))
                .stream()
                .map(Response::getId)
                .collect(toList());
        responseRepository.updateResponsesStatus(status, ids);
    }

}
