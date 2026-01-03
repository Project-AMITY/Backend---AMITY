package org.lms.repository;

import org.lms.entity.AdminRequestData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRequestRepository extends JpaRepository<AdminRequestData,Long> {
}
