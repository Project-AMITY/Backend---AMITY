package org.lms.service.Impl;

import lombok.RequiredArgsConstructor;
import org.lms.dto.EventDto;
import org.lms.dto.UserDto;
import org.lms.entity.Event;
import org.lms.entity.User;
import org.lms.exception.EventNotFoundException;
import org.lms.exception.UnauthorizedAccessException;
import org.lms.exception.UserNotFoundException;
import org.lms.repository.EventRepository;
import org.lms.repository.UserRepository;
import org.lms.utill.Role;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequestMapping
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final EventRepository eventRepository;


    public UserDto getProfile() {
        User user = getCurrentUser();
        return mapper.map(user, UserDto.class);
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public void subscribe() {
        User user = getCurrentUser();
        user.setSubscribed(true);
        userRepository.save(user);
    }

    public void unsubscribe() {
        User user = getCurrentUser();
        user.setSubscribed(false);
        userRepository.save(user);
    }

    public List<EventDto> getFavorites() {
        User user = getCurrentUser();
        return user.getFavorites().stream().map(event -> mapper.map(event, EventDto.class)).collect(Collectors.toList());
    }

    public void addToFavorites(Long eventId) {
        User user = getCurrentUser();
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found"));
        user.getFavorites().add(event);
        userRepository.save(user);
    }

    public void removeFromFavorites(Long eventId) {
        User user = getCurrentUser();
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found"));
        user.getFavorites().remove(event);
        userRepository.save(user);
    }


}
