
package com.aryan.hookflow.service;

import com.aryan.hookflow.entity.WebhookDelivery;
import com.aryan.hookflow.repository.WebhookDeliveryRepository;
import com.aryan.hookflow.dto.WebhookDeliveryResponse;

import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.transaction.annotation.Transactional;

import java.net.http.HttpClient;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public class WebhookDeliveryService {

    private static final int MAX_ATTEMPTS = 4;

    private final WebhookDeliveryRepository deliveryRepository;
    private final RestClient restClient;

    public WebhookDeliveryService(
            WebhookDeliveryRepository deliveryRepository) {

        this.deliveryRepository = deliveryRepository;

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        JdkClientHttpRequestFactory requestFactory =
                new JdkClientHttpRequestFactory(httpClient);

        requestFactory.setReadTimeout(Duration.ofSeconds(10));

        this.restClient = RestClient.builder()
                .requestFactory(requestFactory)
                .build();
    }

    public void deliver(WebhookDelivery delivery) {

        delivery.setAttemptCount(
                delivery.getAttemptCount() + 1
        );

        try {

            var response = restClient.post()
                    .uri(delivery.getSubscription().getTargetUrl())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(delivery.getPayload())
                    .retrieve()
                    .toEntity(String.class);

            delivery.setStatus("SUCCESS");
            delivery.setResponseStatus(
                    response.getStatusCode().value()
            );
            delivery.setResponseBody(response.getBody());
            delivery.setDeliveredAt(LocalDateTime.now());
            delivery.setNextAttemptAt(null);

        } catch (RestClientResponseException exception) {

            handleFailure(
                    delivery,
                    exception.getResponseBodyAsString()
            );

            delivery.setResponseStatus(
                    exception.getStatusCode().value()
            );

        } catch (Exception exception) {

            handleFailure(
                    delivery,
                    "Delivery failed: " + exception.getMessage()
            );
        }

        deliveryRepository.save(delivery);
    }

    private void handleFailure(
            WebhookDelivery delivery,
            String errorMessage) {

        delivery.setStatus("FAILED");
        delivery.setResponseBody(errorMessage);
        delivery.setDeliveredAt(null);

        int attempts = delivery.getAttemptCount();

        if (attempts < MAX_ATTEMPTS) {

            long delayMinutes =
                    (long) Math.pow(2, attempts - 1);

            delivery.setNextAttemptAt(
                    LocalDateTime.now().plus(
                            Duration.ofMinutes(delayMinutes)
                    )
            );

        } else {

            delivery.setNextAttemptAt(null);
        }
    }

    @Transactional(readOnly = true)
    public List<WebhookDeliveryResponse> getAllDeliveries() {

        return deliveryRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(WebhookDeliveryResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<WebhookDeliveryResponse> getDeliveryById(Long id) {

        return deliveryRepository.findById(id)
                .map(WebhookDeliveryResponse::new);
    }
}
