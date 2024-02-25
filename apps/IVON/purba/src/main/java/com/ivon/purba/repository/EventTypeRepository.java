package com.ivon.purba.repository;

import com.ivon.purba.domain.ContentType;
import com.ivon.purba.domain.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Long> {
    Optional<EventType> findByName(String typeName);
}
