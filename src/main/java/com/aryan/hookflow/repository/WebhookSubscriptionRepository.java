
package com.aryan.hookflow.repository;

import com.aryan.hookflow.entity.WebhookSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebhookSubscriptionRepository
        extends JpaRepository<WebhookSubscription, Long> {

    List<WebhookSubscription> findByEventTypeAndActiveTrue(
            String eventType
    );
}
