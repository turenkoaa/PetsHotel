package com.aaturenko.pethotel.controllers;

import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.dto.ResponseDto;
import com.aaturenko.pethotel.entities.Request;
import com.aaturenko.pethotel.entities.Response;
import com.aaturenko.pethotel.entities.Sitter;
import com.aaturenko.pethotel.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/response")
public class ResponseController {

    @GetMapping("/all-by-request/{requestId}")
    public ResponseEntity<List<Response>> findResponsesForRequest(
            @PathVariable long requestId) {
        return ResponseEntity.ok(Request.find(requestId).getResponses());
    }

    @GetMapping("/all-by-author/{authorId}")
    public ResponseEntity<List<Response>> findRequestsByAuthor(
            @PathVariable long authorId) {
        Sitter sitter = (Sitter) User.find(authorId);
        return ResponseEntity.ok(sitter.getResponses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> findResponse(
            @PathVariable long id) {
        return ResponseEntity.ok(Response.find(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createResponse(@RequestBody ResponseDto responseDto) {
        return ResponseEntity.ok(Response.newResponse(responseDto, null));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteResponse(@PathVariable long id) {
        Response.find(id).delete();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
