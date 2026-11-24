
package com.aryan.hookflow.dto;

import com.aryan.hookflow.entity.WebhookDelivery;

import java.time.LocalDateTime;

public class WebhookDeliveryResponse {

    private Long id;
    private Long subscriptionId;
    private String targetUrl;
    private String eventType;
    private String payload;
    private String status;
    private Integer responseStatus;
    private String responseBody;
    private int attemptCount;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;

    public WebhookDeliveryResponse(WebhookDelivery delivery) {
        this.id = delivery.getId();
        this.subscriptionId = delivery.getSubscription().getId();
        this.targetUrl = delivery.getSubscription().getTargetUrl();
        this.eventType = delivery.getEventType();
        this.payload = delivery.getPayload();
        this.status = delivery.getStatus();
        this.responseStatus = delivery.getResponseStatus();
        this.responseBody = delivery.getResponseBody();
        this.attemptCount = delivery.getAttemptCount();
        this.createdAt = delivery.getCreatedAt();
        this.deliveredAt = delivery.getDeliveredAt();
    }

    public Long getId() {
        return id;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public String getEventType() {
        return eventType;
    }

    public String getPayload() {
        return payload;
    }

    public String getStatus() {
        return status;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }
}
