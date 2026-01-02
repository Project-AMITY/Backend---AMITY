package org.lms.dto;

import lombok.Data;
import org.lms.utill.Role;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String first_name;
    private String last_name;
    private String email;
    private String university;
    private Role role;
    private Boolean subscribed;
    private LocalDateTime createdAt;

}
