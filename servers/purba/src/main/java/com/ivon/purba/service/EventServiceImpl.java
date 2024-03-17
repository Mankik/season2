package com.ivon.purba.service;

import com.ivon.purba.domain.*;
import com.ivon.purba.dto.aiService.EventAnalysisResponse;
import com.ivon.purba.dto.eventController.EventPostRequest;
import com.ivon.purba.dto.eventController.EventUpdateRequest;
import com.ivon.purba.exception.AIAnalysisException;
import com.ivon.purba.exception.ResourceNotFoundException;
import com.ivon.purba.repository.EventRepository;
import com.ivon.purba.service.serviceInterface.EventService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("없는 이벤트입니다.: " + eventId));

        if (Boolean.TRUE.equals(event.getEasyDelete())) {
            throw new ResourceNotFoundException("이미 삭제된 이벤트입니다.: " + eventId);
        }

        return event;
    }

    @Override
    public Long createEvent(ContentType contentType, EventType eventType, User user, EventPostRequest request) {
        Event event = new Event.Builder()
                .contentType(contentType)
                .eventType(eventType)
                .user(user)
                .location(request.getLocation())
                .title(request.getTitle())
                .data(request.getData())
                .photoUrl(request.getPhotoUrl())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .charge(request.getCharge())
                .bankAccount(request.getBackAccount())
                .summary(request.getSummary())
                .build();

        return saveEvent(event);
    }

    @Override
    public void updateEvent(Long eventId, EventType eventType, User user, EventUpdateRequest request) {
        Event event = getEvent(eventId);
        setEventAttributes(event, eventType, user, request);
        saveEvent(event);
    }

    @Override
    public void deleteEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("없는 이벤트입니다.: " + eventId));

        if (Boolean.TRUE.equals(event.getEasyDelete())) {
            throw new ResourceNotFoundException("이미 삭제된 이벤트입니다.: " + eventId);
        }

        event.setEasyDelete(true);
        saveEvent(event);
    }

    public Event cancelDeleteEventById(Long eventId){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("복구할 수 없는 이벤트입니다.: " + eventId));

        event.setEasyDelete(false);
        saveEvent(event);
        return event;
    }

    @Override
    public List<Event> getAllEventsByLocation(String location) {
        return eventRepository.findAllByLocation(location);
    }

    @Override
    public Long analyzeAndSaveEventDetails(Event event) throws AIAnalysisException {
        try {
            EventAnalysisResponse analyzedEvent = aiService.analyzeEvent(event);

            if (analyzedEvent.getSummary() != null) {
                event.setSummary(analyzedEvent.getSummary());
            }
            if (analyzedEvent.getStartDate() != null) {
                event.setStartDate(analyzedEvent.getStartDate());
            }
            if (analyzedEvent.getEndDate() != null) {
                event.setEndDate(analyzedEvent.getEndDate());
            }
            if (analyzedEvent.getCharge() != null) {
                event.setCharge(analyzedEvent.getCharge());
            }
            if (analyzedEvent.getBankAccount() != null) {
                event.setBankAccount(analyzedEvent.getBankAccount());
            }

            return saveEvent(event);
        } catch (Exception e) {
            throw new AIAnalysisException();
        }
    }

    private void setEventAttributes(Event event, EventType eventType, User user, EventUpdateRequest request) {
        if (eventType != null) {
            event.setEventType(eventType);
        }
        if (user != null) {
            event.setUser(user);
        }
        if (request.getTitle() != null) {
            event.setTitle(request.getTitle());
        }
        if (request.getData() != null) {
            event.setData(request.getData());
        }
        if (request.getPhotoUrl() != null) {
            event.setPhotoUrl(request.getPhotoUrl());
        }
        if (request.getStartDate() != null) {
            event.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            event.setEndDate(request.getEndDate());
        }
        if (request.getCharge() != null) {
            event.setCharge(request.getCharge());
        }
        if (request.getBackAccount() != null) {
            event.setBankAccount(request.getBackAccount());
        }
    }
}

