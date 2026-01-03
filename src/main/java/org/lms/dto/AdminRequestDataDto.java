package org.lms.dto;

import lombok.Data;

@Data
public class AdminRequestDataDto {
    private Long id;
    private String first_name;
    private String last_name;
    private String designation;
    private String email;
    private String  nic;
    private String university;
    private Integer phone_number;
    private String approveal;
}
