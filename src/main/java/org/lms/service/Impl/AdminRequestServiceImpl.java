package org.lms.service.Impl;

import lombok.RequiredArgsConstructor;
import org.lms.dto.AdminRequestDataDto;
import org.lms.entity.AdminRequestData;
import org.lms.repository.AdminRequestRepository;
import org.lms.service.AdminRequestService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminRequestServiceImpl implements AdminRequestService {

    private final AdminRequestRepository repository;

    @Override
    public AdminRequestData createAdminRequest(AdminRequestDataDto dto) {
        AdminRequestData entity = AdminRequestData.builder()
                .first_name(dto.getFirst_name())
                .last_name(dto.getLast_name())
                .designation(dto.getDesignation())
                .email(dto.getEmail())
                .nic(dto.getNic())
                .university(dto.getUniversity())
                .phone_number(dto.getPhone_number())
                .approveal(dto.getApproveal())
                .build();

        return repository.save(entity);
    }
}
