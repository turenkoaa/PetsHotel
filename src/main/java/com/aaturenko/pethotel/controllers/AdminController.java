package com.aaturenko.pethotel.controllers;

import com.aaturenko.pethotel.entities.Admin;
import com.aaturenko.pethotel.entities.Sitter;
import com.aaturenko.pethotel.entities.User;
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
        admin.blockUser(Sitter.find(userId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users-to-block")
    public ResponseEntity<List<User>> showUsersToBlock() {
        return ResponseEntity.ok(admin.showUsersToBlock());
    }
}
