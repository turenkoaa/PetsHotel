package com.aaturenko.pethotel.controllers;

import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.dto.ResponseDto;
import com.aaturenko.pethotel.old.models.Pet;
import com.aaturenko.pethotel.old.models.Request;
import com.aaturenko.pethotel.old.models.Response;
import com.aaturenko.pethotel.old.services.ManageStatusesService;
import com.aaturenko.pethotel.old.services.RequestService;
import com.aaturenko.pethotel.old.services.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/response")
public class ResponseController {
    private final RequestService requestService;
    private final ResponseService responseService;
    private final ManageStatusesService manageStatusesService;

    @GetMapping("/all-by-request/{requestId}")
    public ResponseEntity<List<Response>> findResponsesForRequest(
            @PathVariable long requestId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(responseService.findAllResponsesByRequestId(requestId, page, size));
    }

    @GetMapping("/all-by-author/{authorId}")
    public ResponseEntity<List<Response>> findRequestsByAuthor(
            @PathVariable long authorId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(responseService.findAllResponsesByUserId(authorId, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> findResponse(
            @PathVariable long id) {
        return ResponseEntity.ok(responseService.findResponseById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createResponse(@RequestBody ResponseDto responseDto) {
        return ResponseEntity.ok(responseService.createResponse(responseDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteResponse(@PathVariable long id) {
        responseService.deleteResponseById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<?> acceptResponse(@PathVariable long id) {
        manageStatusesService.acceptResponse(
                responseService.findResponseById(id)
        );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
