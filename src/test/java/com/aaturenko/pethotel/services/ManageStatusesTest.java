package com.aaturenko.pethotel.services;

import com.aaturenko.pethotel.dto.ResponseDto;
import com.aaturenko.pethotel.entities.Request;
import com.aaturenko.pethotel.entities.Response;
import com.aaturenko.pethotel.entities.User;
import com.aaturenko.pethotel.exceptions.EntityNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.aaturenko.pethotel.enums.RequestStatus.SOLVED;
import static com.aaturenko.pethotel.enums.ResponseStatus.ACCEPTED;
import static com.aaturenko.pethotel.enums.ResponseStatus.REJECTED;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ManageStatusesTest extends DatabaseTest {

    private Response response;
    private Request request;
    private long requestId = 1L;
    private long sitterId = 5L;


    @Before
    public void initDb() {
        ResponseDto responseDto = new ResponseDto()
                .setAuthorId(sitterId)
                .setCost(500)
                .setRequestId(requestId);
        response = Response.newResponse(responseDto, User.find(sitterId));
        request = Request.find(requestId);

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
    public void successfullyAcceptResponse() {
        request.acceptResponse(response.getId());
        assertThat(Request.find(requestId).getStatus(), is(SOLVED));
        assertThat(Response.find(1L).getStatus(), is(REJECTED));
        assertThat(Response.find(response.getId()).getStatus(), is(ACCEPTED));
    }

    @Test
    public void successfullyDeleteResponse() {
        request.acceptResponse(response.getId());
        response.delete();

        assertThat(Request.find(requestId).getStatus(), is(SOLVED));
        assertThat(Response.find(1L).getStatus(), is(REJECTED));
    }
}
