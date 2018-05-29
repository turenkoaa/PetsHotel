package com.aaturenko.pethotel.controllers;

import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.entities.Owner;
import com.aaturenko.pethotel.entities.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/request")
public class RequestController {

    @GetMapping("/all-new")
    public ResponseEntity<List<Request>> findNewRequests() {
        return ResponseEntity.ok(Request.findNewRequests());
    }

    @GetMapping("/all-by-author/{authorId}")
    public ResponseEntity<List<Request>> findRequestsByAuthor(
            @PathVariable long authorId) {
        return ResponseEntity.ok(Owner.find(authorId).requests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> findRequest(
            @PathVariable long id) {
        return ResponseEntity.ok(Request.find(id));
    }
    @PostMapping("/create")
    public ResponseEntity<Request> createRequest(@RequestBody RequestDto requestDto) {
        return ResponseEntity.ok(Owner.find(requestDto.getUserId()).addRequest(requestDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteRequest(@PathVariable long id) {
        Request.find(id).delete();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/anulled")
    public ResponseEntity<?> anulledRequest(@PathVariable long id) {
        Request.find(id).annuled();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}/reject-responses")
    public ResponseEntity<?> rejectAllResponsesForRequest(@PathVariable long id) {
        Request.find(id).rejectResponses();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}/accept/{responseId}")
    public ResponseEntity<?> acceptResponse(@PathVariable long id, @PathVariable long responseId) {
        Request.find(id).acceptResponse(responseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
