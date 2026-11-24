
package com.aryan.hookflow.service;

import com.aryan.hookflow.entity.WebhookSubscription;
import com.aryan.hookflow.repository.WebhookSubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebhookSubscriptionService {

    private final WebhookSubscriptionRepository repository;

    public WebhookSubscriptionService(
            WebhookSubscriptionRepository repository) {
        this.repository = repository;
    }

    public WebhookSubscription createSubscription(
            String targetUrl, String eventType) {

        WebhookSubscription subscription =
                new WebhookSubscription(targetUrl, eventType);

        return repository.save(subscription);
    }

    public List<WebhookSubscription> getAllSubscriptions() {
        return repository.findAll();
    }
}
