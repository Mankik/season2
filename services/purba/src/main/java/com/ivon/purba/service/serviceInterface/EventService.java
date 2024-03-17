package com.ivon.purba.service.serviceInterface;


import com.ivon.purba.domain.ContentType;
import com.ivon.purba.domain.Event;
import com.ivon.purba.domain.EventType;
import com.ivon.purba.domain.User;
import com.ivon.purba.dto.eventController.EventPostRequest;
import com.ivon.purba.dto.eventController.EventUpdateRequest;
import com.ivon.purba.exception.AIAnalysisException;

import java.util.Date;
import java.util.List;

public interface EventService {

    Long saveEvent(Event event);

    Event getEvent(Long id);

    Long createEvent(ContentType contentType, EventType eventType, User user, EventPostRequest request);

    void updateEvent(Long eventId, EventType eventType, User user, EventUpdateRequest request);

    void deleteEventById(Long id);

    List<Event> getAllEventsByLocation(String location);

    Long analyzeAndSaveEventDetails(Event event) throws AIAnalysisException;
}
