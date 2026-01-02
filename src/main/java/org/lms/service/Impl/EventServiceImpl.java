package org.lms.service.Impl;

import lombok.RequiredArgsConstructor;
import org.lms.dto.EventDto;
import org.lms.entity.Event;
import org.lms.entity.User;
import org.lms.exception.UnauthorizedAccessException;
import org.lms.repository.EventRepository;
import org.lms.repository.UserRepository;
import org.lms.service.EventService;
import org.lms.utill.Role;
import org.modelmapper.ModelMapper;
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

    /* ================= HELPERS ================= */

    public User getCurrentUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return (User) userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UnauthorizedAccessException("Authenticated user not found"));
    }

    public void ensureAdmin(User user) {
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.SUPER_ADMIN) {
            throw new UnauthorizedAccessException("Only admins can create events");
        }
    }
}
