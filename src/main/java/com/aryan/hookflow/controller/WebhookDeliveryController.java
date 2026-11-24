
package com.aryan.hookflow.controller;

import com.aryan.hookflow.dto.WebhookDeliveryResponse;
import com.aryan.hookflow.service.WebhookDeliveryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deliveries")
public class WebhookDeliveryController {

    private final WebhookDeliveryService deliveryService;

    public WebhookDeliveryController(
            WebhookDeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping
    public List<WebhookDeliveryResponse> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebhookDeliveryResponse> getDeliveryById(
            @PathVariable Long id) {

        return deliveryService.getDeliveryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
