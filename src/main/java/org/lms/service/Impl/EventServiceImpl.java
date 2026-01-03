package org.lms.service.Impl;

import lombok.RequiredArgsConstructor;
import org.lms.dto.EventDto;
import org.lms.entity.Event;
import org.lms.entity.User;
import org.lms.exception.EventNotFoundException;
import org.lms.exception.UnauthorizedAccessException;
import org.lms.repository.EventRepository;
import org.lms.repository.UserRepository;
import org.lms.service.EventService;
import org.lms.utill.EventType;
import org.lms.utill.Role;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public EventDto createEvent(EventDto dto) {
        User user = getCurrentUser();
        ensureAdmin(user);

        Event savedEvent = eventRepository.save(mapper.map(dto,Event.class));
        return mapper.map(savedEvent,EventDto.class);
    }

    @Override
    public EventDto updateEvent(Long id, EventDto dto) {
        User user = getCurrentUser();
        Event event = findEventById(id);

        ensureOwnerOrAdmin(user, event);

        updateEntity(event, dto);
        Event updatedEvent = eventRepository.save(event);

        return mapper.map(updatedEvent, EventDto.class);
    }

    @Override
    public void deleteEvent(Long id) {
        User user = getCurrentUser();
        Event event = findEventById(id);

        ensureOwnerOrAdmin(user, event);
        eventRepository.delete(event);
    }

    @Override
    public Page<EventDto> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable)
                .map(event -> mapper.map(event, EventDto.class));
    }

    @Override
    public EventDto getEventById(Long id) {
        return mapper.map(findEventById(id), EventDto.class);
    }

    @Override
    public Page<EventDto> getEventsByCategory(String category, Pageable pageable) {
            return eventRepository.findByCategory(category, pageable)
                    .map(event -> mapper.map(event, EventDto.class));
    }

    @Override
    public Page<EventDto> getEventsByUniversity(String university, Pageable pageable) {
        return eventRepository.findByUniversity(university, pageable)
                .map(event -> mapper.map(event, EventDto.class));
    }

    @Override
    public Page<EventDto> getEventByEventType(EventType eventType, Pageable pageable) {
        return eventRepository.findByEventType(eventType,pageable)
                .map(event -> mapper.map(event,EventDto.class));
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UnauthorizedAccessException("Authenticated user not found"));
    }

    public void ensureAdmin(User user) {
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.SUPER_ADMIN) {
            throw new UnauthorizedAccessException("Only admins can create events");
        }
    }

    public void ensureOwnerOrAdmin(User user, Event event) {
        boolean isAdmin = user.getRole() == Role.ADMIN || user.getRole() == Role.SUPER_ADMIN;

        boolean isOwner = event.getCreatedBy() != null
                && event.getCreatedBy().getId().equals(user.getId());

        if (!isOwner && !isAdmin) {
            throw new UnauthorizedAccessException("Unauthorized to modify this event");
        }
    }

    public Event findEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() ->
                        new EventNotFoundException("Event not found with id: " + id));
    }

    public void updateEntity(Event event, EventDto dto) {
        event.setTitle(dto.getTitle());
        event.setImage(dto.getImage());
        event.setDescription(dto.getDescription());
        event.setHighlight(dto.getHighlight());
        event.setUniversity(dto.getUniversity());
        event.setOrganizer(dto.getOrganizer());
        event.setCategory(dto.getCategory());
        event.setEventType(dto.getEventType());
        event.setEventDate(dto.getEventDate());
        event.setEventTime(dto.getEventTime());
        event.setContactlink(dto.getContactlink());
        event.setFee(dto.getFee());
    }
}
