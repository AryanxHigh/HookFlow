
package com.aryan.hookflow.controller;

import com.aryan.hookflow.dto.CreateWebhookRequest;
import com.aryan.hookflow.dto.WebhookResponse;
import com.aryan.hookflow.entity.WebhookSubscription;
import com.aryan.hookflow.service.WebhookSubscriptionService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/webhooks")
public class WebhookSubscriptionController {

    private final WebhookSubscriptionService service;

    public WebhookSubscriptionController(
            WebhookSubscriptionService service) {
        this.service = service;
    }

    @PostMapping
    public WebhookResponse createSubscription(
            @Valid @RequestBody CreateWebhookRequest request) {

        WebhookSubscription subscription =
                service.createSubscription(
                        request.getTargetUrl(),
                        request.getEventType()
                );

        return new WebhookResponse(subscription);
    }

    @GetMapping
    public List<WebhookResponse> getAllSubscriptions() {

        return service.getAllSubscriptions()
                .stream()
                .map(WebhookResponse::new)
                .toList();
    }
}
