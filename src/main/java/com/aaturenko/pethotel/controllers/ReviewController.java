package com.aaturenko.pethotel.controllers;

import com.aaturenko.pethotel.dto.ReviewDto;
import com.aaturenko.pethotel.model.Review;
import com.aaturenko.pethotel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<Review> addReview(@RequestBody ReviewDto reviewDto){
        return ResponseEntity.ok(userService.addReview(reviewDto));
    }
}
