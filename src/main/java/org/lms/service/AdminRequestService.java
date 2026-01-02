package org.lms.service;

import org.lms.dto.AdminRequestDataDto;
import org.lms.entity.AdminRequestData;

public interface AdminRequestService {
    AdminRequestData createAdminRequest(AdminRequestDataDto dto);
}
