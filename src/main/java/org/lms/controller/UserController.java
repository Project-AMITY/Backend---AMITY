package org.lms.controller;

import lombok.RequiredArgsConstructor;
import org.lms.dto.EventDto;
import org.lms.dto.UserDto;
import org.lms.service.Impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile() {
        return ResponseEntity.ok(userServiceImpl.getProfile());
    }



    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe() {
        userServiceImpl.subscribe();
        return ResponseEntity.ok("Subscribed");
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe() {
        userServiceImpl.unsubscribe();
        return ResponseEntity.ok("Unsubscribed");
    }


    @GetMapping("/favorites")
    public ResponseEntity<List<EventDto>> getFavorites() {
        return ResponseEntity.ok(userServiceImpl.getFavorites());
    }

    @PostMapping("/favorites/{eventId}")
    public ResponseEntity<String> addToFavorites(@PathVariable Long eventId) {
        userServiceImpl.addToFavorites(eventId);
        return ResponseEntity.ok("Added to favorites");
    }

    @DeleteMapping("/favorites/{eventId}")
    public ResponseEntity<String> removeFromFavorites(@PathVariable Long eventId) {
        userServiceImpl.removeFromFavorites(eventId);
        return ResponseEntity.ok("Removed from favorites");
    }

}
