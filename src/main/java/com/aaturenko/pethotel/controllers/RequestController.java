package com.aaturenko.pethotel.controllers;

import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.model.Request;
import com.aaturenko.pethotel.service.impl.PaymentService;
import com.aaturenko.pethotel.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/all-new/{userId}")
    public ResponseEntity<List<Request>> findNewRequests(
            @PathVariable long userId) {
        return ResponseEntity.ok(requestService.findNewRequestsForUser(userId));
    }

    @GetMapping("/all-by-author/{authorId}")
    public ResponseEntity<List<Request>> findRequestsByAuthor(
            @PathVariable long authorId) {
        return ResponseEntity.ok(requestService.findAllRequestsOfUser(authorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> findRequest(
            @PathVariable long id) {
        return ResponseEntity.ok(requestService.findById(id));
    }
    @PostMapping("/create")
    public ResponseEntity<Request> createRequest(@RequestBody RequestDto requestDto) {
        return ResponseEntity.ok(requestService.saveRequest(requestDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteRequest(@PathVariable long id) {
        requestService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/anulled")
    public ResponseEntity<?> anulledRequest(@PathVariable long id) {
        requestService.annuledById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}/reject-responses")
    public ResponseEntity<?> rejectAllResponsesForRequest(@PathVariable long id) {
        requestService.rejectAllResponsesById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}/accept/{responseId}")
    public ResponseEntity<?> acceptResponse(@PathVariable long id, @PathVariable long responseId) {
        requestService.acceptResponse(id, responseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Autowired
    PaymentService paymentService;

    @PutMapping("/{id}/paid")
    public ResponseEntity<?> paidConfirmed(@PathVariable long id) {
        Request request = requestService.findById(id);
        boolean paymentSuccess = paymentService.pay(request.getUser(), request.getCost());
        if (paymentSuccess) {
            requestService.confirmPayment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.badRequest().build();
    }

}
