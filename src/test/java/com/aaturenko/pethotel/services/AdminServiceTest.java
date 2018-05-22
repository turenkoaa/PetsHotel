package com.aaturenko.pethotel.services;

import com.aaturenko.pethotel.enums.RequestStatus;
import com.aaturenko.pethotel.repositories.RequestRepository;
import com.aaturenko.pethotel.repositories.ResponseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.aaturenko.pethotel.enums.RequestStatus.ANULLED;
import static com.aaturenko.pethotel.enums.ResponseStatus.REJECTED;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;

public class AdminServiceTest extends DatabaseTest {

    @Autowired private AdminService adminService;
    @Autowired private UserService userService;
    @Autowired private RequestRepository requestRepository;
    @Autowired private ResponseRepository responseRepository;

    @Test
    public void successUserActivation() {
        long userId = 6L;
        adminService.activateUser(userId);
        assertTrue(userService.findUserById(userId).getActive());
    }

    @Test
    public void successUserBlocking() {
        long userId = 1L;
        adminService.blockUser(userId);
        assertFalse(userService.findUserById(userId).getActive());
        requestRepository.findAllByUser_Id(userId). forEach(
                request -> {
                    assertThat(request.getStatus(), is(ANULLED));
                    responseRepository.findAllByRequest_Id(request.getId()).forEach(
                            response -> assertThat(response.getStatus(), is(REJECTED))
                    );
                }
        );

    }
}
