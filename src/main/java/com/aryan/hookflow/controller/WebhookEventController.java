
package com.aryan.hookflow.controller;

import com.aryan.hookflow.dto.PublishEventRequest;
import com.aryan.hookflow.service.WebhookEventService;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/events")
public class WebhookEventController {

    private final WebhookEventService eventService;
    private final ObjectMapper objectMapper;

    public WebhookEventController(
            WebhookEventService eventService,
            ObjectMapper objectMapper) {

        this.eventService = eventService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> publishEvent(
            @Valid @RequestBody PublishEventRequest request)
            throws JacksonException {

        String payload = objectMapper.writeValueAsString(
                request.getPayload()
        );

        int matchedSubscriptions = eventService.publishEvent(
                request.getEventType(),
                payload
        );

        Map<String, Object> response = Map.of(
                "message", "Event processed successfully",
                "eventType", request.getEventType(),
                "matchedSubscriptions", matchedSubscriptions
        );

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(response);
    }
}
