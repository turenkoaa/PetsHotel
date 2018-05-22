package com.aaturenko.pethotel.services;

import com.aaturenko.pethotel.dto.ResponseDto;
import com.aaturenko.pethotel.exceptions.EntityNotFoundException;
import com.aaturenko.pethotel.models.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.aaturenko.pethotel.enums.RequestStatus.NEW;
import static com.aaturenko.pethotel.enums.RequestStatus.SOLVED;
import static com.aaturenko.pethotel.enums.ResponseStatus.ACCEPTED;
import static com.aaturenko.pethotel.enums.ResponseStatus.PROPOSED;
import static com.aaturenko.pethotel.enums.ResponseStatus.REJECTED;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ManageStatusesServiceTest extends DatabaseTest {

    private Response response;
    private long requestId = 1L;
    private long sitterId = 5L;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private ManageStatusesService manageStatusesService;

    @Before
    public void initDb() {
        ResponseDto responseDto = new ResponseDto()
                .setAuthorId(sitterId)
                .setCost(500)
                .setRequestId(requestId);
        response = responseService.createResponse(responseDto);
    }

    @After
    public void deleteDb() {
        try {
            responseService.deleteResponseById(response.getId());
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void successfullyAcceptResponse() {
        manageStatusesService.acceptResponse(response);
        assertThat(requestService.findRequestById(requestId).getStatus(), is(SOLVED));
        assertThat(responseService.findResponseById(1L).getStatus(), is(REJECTED));
        assertThat(responseService.findResponseById(response.getId()).getStatus(), is(ACCEPTED));
    }

    @Test
    public void successfullyDeleteResponse() {
        manageStatusesService.acceptResponse(response);
        responseService.deleteResponseById(response.getId());

        assertThat(requestService.findRequestById(requestId).getStatus(), is(NEW));
        assertThat(responseService.findResponseById(1L).getStatus(), is(PROPOSED));
    }
}
