package com.aaturenko.pethotel.services;

import com.aaturenko.pethotel.dto.ResponseDto;
import com.aaturenko.pethotel.enums.RequestStatus;
import com.aaturenko.pethotel.enums.ResponseStatus;
import com.aaturenko.pethotel.exceptions.EntityNotFoundException;
import com.aaturenko.pethotel.models.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.aaturenko.pethotel.enums.RequestStatus.NEW;
import static com.aaturenko.pethotel.enums.RequestStatus.SOLVED;
import static com.aaturenko.pethotel.enums.ResponseStatus.ACCEPTED;
import static com.aaturenko.pethotel.enums.ResponseStatus.PROPOSED;
import static com.aaturenko.pethotel.enums.ResponseStatus.REJECTED;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ResponseServiceTest extends DatabaseTest {

    private Response response;
    private long requestId = 1L;
    private long sitterId = 5L;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private RequestService requestService;

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
    public void savedResponseSuccessfully() {
        assertEquals(sitterId, response.getUser().getId());
        assertEquals(ResponseStatus.PROPOSED, response.getStatus());
        assertNull(response.getDetails());
    }

    @Test(expected = EntityNotFoundException.class)
    public void notFoundResponse() {
        responseService.findResponseById(9999999L);
    }

    @Test
    public void successfullyFoundResponse() {
        responseService.findResponseById(response.getId());
    }

    @Test
    public void successfullyFoundAllResponsesByUserId() {
        List<Response> responses = responseService.findAllResponsesByUserId(sitterId, 0, 5);
        assertThat(responses, hasSize(2));
    }

    @Test
    public void successfullyFoundAllResponsesByRequestId() {
        List<Response> responses = responseService.findAllResponsesByRequestId(requestId, 0, 5);
        assertThat(responses, hasSize(2));
    }
}
