package org.lms.service;

import org.lms.dto.EventDto;
import org.lms.dto.UserDto;
import org.lms.entity.User;

import java.util.List;

public interface UserService {

    UserDto getProfile();

    User getCurrentUser();

    void subscribe();

    void unsubscribe();

    List<EventDto> getFavorites();

    void addToFavorites(Long eventId);

    void removeFromFavorites(Long eventId);
}