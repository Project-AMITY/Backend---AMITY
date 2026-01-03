package org.lms.service;

import org.lms.dto.EventDto;
import org.lms.utill.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {
    EventDto createEvent(EventDto dto);

    EventDto updateEvent(Long id, EventDto dto);

    void deleteEvent(Long id);

    Page<EventDto> getAllEvents(Pageable pageable);

    EventDto getEventById(Long id);

    Page<EventDto> getEventsByCategory(String category, Pageable pageable);

    Page<EventDto> getEventsByUniversity(String university, Pageable pageable);

    Page<EventDto> getEventByEventType(EventType eventType, Pageable pageable);
}
