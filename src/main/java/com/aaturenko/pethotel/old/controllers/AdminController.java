package com.aaturenko.pethotel.old.controllers;

import com.aaturenko.pethotel.old.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
