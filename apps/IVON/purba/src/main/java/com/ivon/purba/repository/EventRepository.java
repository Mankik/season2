package com.ivon.purba.repository;


import com.ivon.purba.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.id  = :eventId")
    Optional<Event> findByContentId(@Param("eventId") Long eventId);
    List<Event> findAllByLocation(String location);
}
