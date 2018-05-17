package com.aaturenko.pethotel.controllers;

import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.models.Request;
import com.aaturenko.pethotel.models.User;
import com.aaturenko.pethotel.services.ManageStatusesService;
import com.aaturenko.pethotel.services.RequestService;
import com.aaturenko.pethotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;
    private final ManageStatusesService manageStatusesService;

    @GetMapping("/all-new")
    public ResponseEntity<List<Request>> findNewRequests(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(requestService.findAllNewRequests(page, size));
    }

    @GetMapping("/all-by-author/{authorId}")
    public ResponseEntity<List<Request>> findRequestsByAuthor(
            @PathVariable long authorId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(requestService.findAllRequestsByAuthor(authorId, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> findRequest(
            @PathVariable long id) {
        return ResponseEntity.ok(requestService.findRequestById(id));
    }
    @PostMapping("/create")
    public ResponseEntity<Request> createRequest(@RequestBody RequestDto requestDto) {
        return ResponseEntity.ok(requestService.createRequest(requestDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteRequest(@PathVariable long id) {
        requestService.deleteRequestById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/anulled")
    public ResponseEntity<?> anulledRequest(@PathVariable long id) {
        manageStatusesService.anulledRequestAndRejectItsResponses(
                requestService.findRequestById(id)
        );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}/reject-responses")
    public ResponseEntity<?> rejectAllResponsesForRequest(@PathVariable long id) {
        manageStatusesService.rejectAllResponsesForRequest(
                requestService.findRequestById(id)
        );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
