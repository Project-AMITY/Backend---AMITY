package org.lms.repository;

import org.lms.dto.EventDto;
import org.lms.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
}
