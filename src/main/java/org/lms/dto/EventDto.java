package org.lms.dto;

import lombok.Data;
import org.lms.utill.EventType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class EventDto {
    private Long id;
    private String title;
    private String image;
    private String description;
    private String highlight;
    private String category;
    private String university;
    private String organizer;
    private EventType eventType;
    private LocalDate eventDate;
    private LocalTime eventTime;
    private String contactlink;
    private String fee;
    private Long createdById;
    private LocalDateTime createdAt;


}
