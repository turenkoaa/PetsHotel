package com.aaturenko.pethotel.controllers;


import com.aaturenko.pethotel.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/all")
    public ResponseEntity<List<User>> findUsers() {
        return ResponseEntity.ok(User.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUser(
            @PathVariable long id) {
        return ResponseEntity.ok(User.find(id));
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<User> findUserByEmail(
            @PathVariable String email) {
        return ResponseEntity.ok(User.findByEmail(email));
    }

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.ok((User) user.save());
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        User.find(id).delete();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
