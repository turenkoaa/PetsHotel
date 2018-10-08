package com.aaturenko.pethotel.services;

import com.aaturenko.pethotel.old.entities.Admin;
import com.aaturenko.pethotel.old.entities.Request;
import com.aaturenko.pethotel.old.entities.User;
import org.junit.Test;

import static com.aaturenko.pethotel.enums.RequestStatus.NEW;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;

public class AdminTest extends DatabaseTest {

    @Test
    public void successUserBlocking() {
        long userId = 2L;
        Admin admin = new Admin();
        admin.blockUser(User.find(2L));
        User user = User.find(userId);
        assertFalse(user.getActive());

        Request.findAllByUser(user). forEach(
                request -> {
                    assertThat(request.getStatus(), is(NEW));
//                    request.responses().forEach(
//                            response -> assertThat(response.getStatus(), is(REJECTED))
//                    );
                }
        );
    }
}
