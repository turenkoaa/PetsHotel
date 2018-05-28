package com.aaturenko.pethotel.controllers;

import com.aaturenko.pethotel.entities.Admin;
import com.aaturenko.pethotel.entities.User;
import com.aaturenko.pethotel.old.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private Admin admin = new Admin();

    @PutMapping("/block-user/{userId}")
    public ResponseEntity<?> blockUser(@PathVariable long userId) {
        admin.blockUser(User.find(userId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users-to-block")
    public List<User> showUsersToBlock() {
        return admin.showUsersToBlock();
    }
}
