package com.aaturenko.pethotel.services;


import com.aaturenko.pethotel.dto.ResponseDto;
import com.aaturenko.pethotel.entities.Request;
import com.aaturenko.pethotel.entities.Response;
import com.aaturenko.pethotel.entities.User;
import com.aaturenko.pethotel.enums.ResponseStatus;
import com.aaturenko.pethotel.exceptions.EntityNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ResponseServiceTest extends DatabaseTest {

    private Response response;
    private User user;
    private long requestId = 1L;
    private long sitterId = 5L;

    @Before
    public void initDb() {
        ResponseDto responseDto = new ResponseDto()
                .setAuthorId(sitterId)
                .setCost(500)
                .setRequestId(requestId);
        user = User.find(sitterId);
        response = Response.newResponse(responseDto, user);
    }

    @After
    public void deleteDb() {
        try {
            response.delete();
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

    @Test
    public void notFoundResponse() {
        assertNull(Response.find(9999999L));
    }

    @Test
    public void successfullyFoundAllResponsesByUserId() {
        List<Response> responses = Response.findAllByUser(user);
        assertThat(responses, hasSize(2));
    }

    @Test
    public void successfullyFoundAllResponsesByRequestId() {
        List<Response> responses = Response.findByRequest(Request.find(requestId));
        assertThat(responses, hasSize(2));
    }
}
