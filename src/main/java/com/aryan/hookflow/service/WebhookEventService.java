
package com.aryan.hookflow.service;

import com.aryan.hookflow.entity.WebhookDelivery;
import com.aryan.hookflow.entity.WebhookSubscription;
import com.aryan.hookflow.repository.WebhookDeliveryRepository;
import com.aryan.hookflow.repository.WebhookSubscriptionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WebhookEventService {

    private final WebhookSubscriptionRepository subscriptionRepository;
    private final WebhookDeliveryRepository deliveryRepository;
    private final WebhookDeliveryService deliveryService;

    public WebhookEventService(
            WebhookSubscriptionRepository subscriptionRepository,
            WebhookDeliveryRepository deliveryRepository,
            WebhookDeliveryService deliveryService) {

        this.subscriptionRepository = subscriptionRepository;
        this.deliveryRepository = deliveryRepository;
        this.deliveryService = deliveryService;
    }

    @Transactional
    public int publishEvent(String eventType, String payload) {

        List<WebhookSubscription> subscriptions =
                subscriptionRepository
                        .findByEventTypeAndActiveTrue(eventType);

        for (WebhookSubscription subscription : subscriptions) {

            WebhookDelivery delivery = new WebhookDelivery();

            delivery.setSubscription(subscription);
            delivery.setEventType(eventType);
            delivery.setPayload(payload);
            delivery.setStatus("PENDING");
            delivery.setAttemptCount(0);

            WebhookDelivery savedDelivery =
                    deliveryRepository.saveAndFlush(delivery);

            deliveryService.deliver(savedDelivery);
        }

        return subscriptions.size();
    }
}
