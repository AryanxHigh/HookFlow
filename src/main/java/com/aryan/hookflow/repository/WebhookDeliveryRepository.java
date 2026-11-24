
package com.aryan.hookflow.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aryan.hookflow.entity.WebhookDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

import java.util.List;

public interface WebhookDeliveryRepository
        extends JpaRepository<WebhookDelivery, Long> {

    List<WebhookDelivery> findAllByOrderByCreatedAtDesc();

    List<WebhookDelivery> findBySubscription_IdOrderByCreatedAtDesc(
            Long subscriptionId);

    List<WebhookDelivery> findByStatus(String status);

    List<WebhookDelivery> findByStatusAndNextAttemptAtLessThanEqual(
            String status,
            LocalDateTime currentTime
    );

    @Query("""
        SELECT d FROM WebhookDelivery d
        JOIN FETCH d.subscription
        WHERE d.status = :status
        AND d.nextAttemptAt <= :currentTime
        """)
    List<WebhookDelivery> findDueDeliveries(
            @Param("status") String status,
            @Param("currentTime") LocalDateTime currentTime
    );
}


