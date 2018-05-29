package com.aaturenko.pethotel.controllers;

import com.aaturenko.pethotel.dto.ReviewDto;
import com.aaturenko.pethotel.entities.Review;
import com.aaturenko.pethotel.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/review")
public class ReviewController {

    @PostMapping("/save")
    public ResponseEntity<Review> addReview(@RequestBody ReviewDto reviewDto){
        return ResponseEntity.ok(User.find(reviewDto.getUserId()).addReview(reviewDto));

    }
}
