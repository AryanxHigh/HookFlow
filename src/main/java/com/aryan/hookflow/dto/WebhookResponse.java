
package com.aryan.hookflow.dto;

import com.aryan.hookflow.entity.WebhookSubscription;

public class WebhookResponse {

    private Long id;
    private String targetUrl;
    private String eventType;
    private boolean active;

    public WebhookResponse(WebhookSubscription subscription) {
        this.id = subscription.getId();
        this.targetUrl = subscription.getTargetUrl();
        this.eventType = subscription.getEventType();
        this.active = subscription.isActive();
    }

    public Long getId() {
        return id;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public String getEventType() {
        return eventType;
    }

    public boolean isActive() {
        return active;
    }
}
