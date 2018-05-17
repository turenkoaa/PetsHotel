package com.aaturenko.pethotel.controllers;

import com.aaturenko.pethotel.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PutMapping("/block-user/{userId}")
    public ResponseEntity<?> blockUser(@PathVariable long userId) {
        adminService.blockUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/activate-user/{userId}")
    public ResponseEntity<?> activateUser(@PathVariable long userId) {
        adminService.activateUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
