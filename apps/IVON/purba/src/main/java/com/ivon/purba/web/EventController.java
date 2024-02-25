package com.ivon.purba.web;


import com.ivon.purba.domain.ContentType;
import com.ivon.purba.domain.Event;
import com.ivon.purba.domain.EventType;
import com.ivon.purba.domain.User;
import com.ivon.purba.dto.eventController.EventGetAllRequest;
import com.ivon.purba.dto.eventController.EventPostRequest;
import com.ivon.purba.dto.eventController.EventPostResponse;
import com.ivon.purba.dto.eventController.EventUpdateRequest;
import com.ivon.purba.dto.smsController.SmsServiceSendResponse;
import com.ivon.purba.service.ContentTypeServiceImpl;
import com.ivon.purba.service.EventServiceImpl;
import com.ivon.purba.service.EventTypeServiceImpl;
import com.ivon.purba.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventServiceImpl eventService;
    private final ContentTypeServiceImpl contentTypeService;
    private final EventTypeServiceImpl eventTypeService;
    private final UserServiceImpl userService;

    @PostMapping("/event/upload")
    public ResponseEntity<EventPostResponse> uploadEvent(@RequestBody EventPostRequest request) {
        ContentType contentType = contentTypeService.getContentType("event");
        EventType eventType = eventTypeService.getEventTypeByName(request.getEventTypeName());
        User user = userService.getUserById(request.getUserId());

        Long eventId = eventService.createEvent(contentType, eventType, user, request);

        EventPostResponse response = new EventPostResponse(eventId,"이벤트 저장 성공");
        response.setMessage("이벤트 저장 성공");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/event/update/")
    public ResponseEntity<String> updateEvent(@RequestParam("eventId") Long eventId, @RequestBody EventUpdateRequest request) {
        Event event = eventService.getEvent(eventId);
        ContentType contentType = contentTypeService.getContentType("event");
        EventType eventType = eventTypeService.getEventTypeByName(request.getContentTypeName());
        User user = userService.getUserById(request.getUserId());

        eventService.updateEvent(event, contentType, eventType, user,request);
        return ResponseEntity.ok("이벤트 업데이트 성공");
    }

    @GetMapping("/event/")
    public ResponseEntity<Event> getEvent(@RequestParam("eventId") Long eventId) {
        Event event = eventService.getEvent(eventId);
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/event/delete/")
    public ResponseEntity<String> deleteEvent(@RequestParam("eventId") Long eventId) {
        eventService.deleteEventById(eventId);
        return ResponseEntity.ok("이벤트 삭제 성공");
    }

    @GetMapping("/event/events")
    public ResponseEntity<List<Event>> getAllEventsByLocation(@RequestBody EventGetAllRequest request) {
        List<Event> events = eventService.getAllEventsByLocation(request.getLocation());
        return ResponseEntity.ok(events);
    }
}
