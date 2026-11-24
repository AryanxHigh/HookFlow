
package com.aryan.hookflow.service;

import com.aryan.hookflow.entity.WebhookDelivery;
import com.aryan.hookflow.repository.WebhookDeliveryRepository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WebhookRetryScheduler {

    private final WebhookDeliveryRepository deliveryRepository;
    private final WebhookDeliveryService deliveryService;

    public WebhookRetryScheduler(
            WebhookDeliveryRepository deliveryRepository,
            WebhookDeliveryService deliveryService) {

        this.deliveryRepository = deliveryRepository;
        this.deliveryService = deliveryService;
    }

    @Scheduled(fixedDelay = 30000)
    public void retryFailedDeliveries() {

        List<WebhookDelivery> failedDeliveries =
                deliveryRepository.findDueDeliveries(
                        "FAILED",
                        LocalDateTime.now()
                );

        for (WebhookDelivery delivery : failedDeliveries) {

            try {
                deliveryService.deliver(delivery);

            } catch (Exception exception) {
                System.err.println(
                        "Retry failed for delivery ID "
                                + delivery.getId()
                                + ": "
                                + exception.getMessage()
                );
            }
        }
    }
}
