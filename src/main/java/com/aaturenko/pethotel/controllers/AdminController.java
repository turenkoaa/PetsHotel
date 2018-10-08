package com.aaturenko.pethotel.controllers;
import com.aaturenko.pethotel.model.User;
import com.aaturenko.pethotel.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PutMapping("/block-user/{userId}")
    public ResponseEntity<?> blockUser(@PathVariable long userId) {
        adminService.blockUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users-to-block")
    public ResponseEntity<List<User>> showUsersToBlock() {
        return ResponseEntity.ok(adminService.showUsersToBlock());
    }
}
