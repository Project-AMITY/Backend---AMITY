package org.lms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.lms.dto.EventDto;
import org.lms.service.EventService;
import org.lms.utill.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/event")

public class EventController {

    private final EventService eventService;

    @PostMapping()
    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody EventDto dto) {
        return new ResponseEntity<>(eventService.createEvent(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDto dto) {
        return ResponseEntity.ok(eventService.updateEvent(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok("Event deleted successfully");
    }

    @GetMapping
    public ResponseEntity<Page<EventDto>> getAllEvents(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(eventService.getAllEvents(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<EventDto>> getEventsByCategory(
            @PathVariable String category,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(eventService.getEventsByCategory(category, pageable));
    }

    @GetMapping("/university/{university}")
    public ResponseEntity<Page<EventDto>> getEventsByUniversity(
            @PathVariable String university,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(eventService.getEventsByUniversity(university, pageable));
    }

    @GetMapping("/university/{event_type}")
    public ResponseEntity<Page<EventDto>> getEventByEventType(
            @PathVariable EventType eventType,
            @PageableDefault(size = 10) Pageable pageable
            ){
        return ResponseEntity.ok(eventService.getEventByEventType(eventType,pageable));
    }
}
