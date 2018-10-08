package com.aaturenko.pethotel.controllers;

import com.aaturenko.pethotel.dto.ResponseDto;
import com.aaturenko.pethotel.model.Response;
import com.aaturenko.pethotel.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/response")
public class ResponseController {

    @Autowired
    private ResponseService responseService;

    @GetMapping("/all-by-request/{requestId}")
    public ResponseEntity<List<Response>> findResponsesForRequest(
            @PathVariable long requestId) {
        return ResponseEntity.ok(responseService.findForRequest(requestId));
    }

    @GetMapping("/all-by-author/{authorId}")
    public ResponseEntity<List<Response>> findRequestsByAuthor(
            @PathVariable long authorId) {
        return ResponseEntity.ok(responseService.findForUser(authorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> findResponse(
            @PathVariable long id) {
        return ResponseEntity.ok(responseService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createResponse(@RequestBody ResponseDto responseDto) {
        return ResponseEntity.ok(responseService.save(responseDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteResponse(@PathVariable long id) {
        responseService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
