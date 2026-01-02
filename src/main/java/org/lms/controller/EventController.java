package org.lms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.lms.dto.EventDto;
import org.lms.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/event")

public class EventController {

    private final EventService eventService;

    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody EventDto dto) {
        return new ResponseEntity<>(eventService.createEvent(dto), HttpStatus.CREATED);
    }
}
