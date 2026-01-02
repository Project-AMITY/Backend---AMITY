package org.lms.controller;

import lombok.RequiredArgsConstructor;
import org.lms.dto.AdminRequestDataDto;
import org.lms.entity.AdminRequestData;
import org.lms.service.AdminRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin-requests")
@RequiredArgsConstructor
public class AdminController {

    private final AdminRequestService service;

    @PostMapping
    public ResponseEntity<AdminRequestData> createAdminRequest(@RequestBody AdminRequestDataDto dto) {
        AdminRequestData savedRequest = service.createAdminRequest(dto);
        return ResponseEntity.ok(savedRequest);
    }
}
