package com.ivon.purba.service;

import com.ivon.purba.domain.*;
import com.ivon.purba.dto.aiService.EventAnalysisResponse;
import com.ivon.purba.dto.aiService.PhotoAnalysisResponse;
import com.ivon.purba.dto.eventController.EventPostRequest;
import com.ivon.purba.dto.eventController.EventUpdateRequest;
import com.ivon.purba.exception.AIAnalysisException;
import com.ivon.purba.exception.ResourceNotFoundException;
import com.ivon.purba.repository.EventRepository;
import com.ivon.purba.service.serviceInterface.EventService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final AiServiceImpl aiService;

    public EventServiceImpl(EventRepository eventRepository){
        this.eventRepository = eventRepository;
        this.aiService = new AiServiceImpl();
    }

    @Override
    public Long saveEvent(Event event) {
        return eventRepository.save(event).getId();
    }

    @Override
    public Event getEvent(Long eventId) {
        return eventRepository.findByContentId(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 이벤트가 존재하지 않습니다.: " + eventId));
    }

    @Override
    public Long createEvent(ContentType contentType, EventType eventType, User user, EventPostRequest request){
        Event event = new Event();
        setEventAttributes(contentType, eventType, user, event, request.getTitle(), request.getData(), request.getPhotoUrl(), request.getStartDate(), request.getEndDate(), request.getCharge(), request.getBackAccount());
        return saveEvent(event);
    }

    @Override
    public void updateEvent(Event event, ContentType contentType, EventType eventType, User user, EventUpdateRequest request) {
        setEventAttributes(contentType, eventType, user, event, request.getTitle(), request.getData(), request.getPhotoUrl(), request.getStartDate(), request.getEndDate(), request.getCharge(), request.getBackAccount());
        saveEvent(event);
    }

    @Override
    public void deleteEventById(Long id){
        eventRepository.findById(id).ifPresent(event -> {
            event.setEasyDelete(true);
            saveEvent(event);
        });
    }

    @Override
    public List<Event> getAllEventsByLocation(String location) {
        return eventRepository.findAllByLocation(location);
    }

    private void setEventAttributes(ContentType contentType, EventType eventType, User user, Event event, String title, String data, String photoUrl, Date startDate, Date endDate, Integer charge, String backAccount) {
        if (contentType != null) {
            event.setContentType(contentType);
        }
        if (eventType != null) {
            event.setEventType(eventType);
        }
        if (user != null) {
            event.setUser(user);
        }
        if (title != null) {
            event.setTitle(title);
        }
        if (data != null) {
            event.setData(data);
        }
        if (photoUrl != null) {
            event.setPhotoUrl(photoUrl);
        }
        if (startDate != null) {
            event.setStartDate(startDate);
        }
        if (endDate != null) {
            event.setEndDate(endDate);
        }
        if (charge != null) {
            event.setCharge(charge);
        }
        if (backAccount != null) {
            event.setBankAccount(backAccount);
        }
    }

    @Override
    public Long analyzeAndSaveEventDetails(Event event) throws AIAnalysisException {
        try {
            EventAnalysisResponse analyzedEvent = aiService.analyzeEvent(event);
            event.setSummary(analyzedEvent.getSummary());
            event.setStartDate(analyzedEvent.getStartDate());
            event.setEndDate(analyzedEvent.getEndDate());
            event.setCharge(analyzedEvent.getCharge());
            event.setBankAccount(analyzedEvent.getBankAccount());
            return saveEvent(event);
        } catch (Exception e) {
            throw new AIAnalysisException();
        }
    }
}
