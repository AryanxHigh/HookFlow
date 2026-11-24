
package com.aryan.hookflow.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

public class CreateWebhookRequest {

    @NotBlank(message = "Target URL is required")
    private String targetUrl;

    @NotBlank(message = "Event type is required")
    @Pattern(
            regexp = "^[A-Z][A-Z0-9_]*$",
            message = "Event type must use uppercase letters, numbers or underscores"
    )
    private String eventType;

    @AssertTrue(message = "Target URL must be a valid public HTTP or HTTPS domain")
    public boolean isTargetUrlValid() {

        if (targetUrl == null || targetUrl.isBlank()) {
            return true;
        }

        try {
            URI uri = new URI(targetUrl.trim());

            String scheme = uri.getScheme();
            String host = uri.getHost();

            if (scheme == null || host == null) {
                return false;
            }

            if (!scheme.equalsIgnoreCase("http")
                    && !scheme.equalsIgnoreCase("https")) {
                return false;
            }

            String normalizedHost = host.toLowerCase(Locale.ROOT);

            if (uri.getUserInfo() != null) {
                return false;
            }

            if (normalizedHost.equals("localhost")
                    || normalizedHost.endsWith(".localhost")
                    || normalizedHost.endsWith(".local")) {
                return false;
            }

            // Require a hostname instead of a direct IP address.
            if (normalizedHost.contains(":")
                    || normalizedHost.matches("[0-9.]+")) {
                return false;
            }

            return true;

        } catch (URISyntaxException exception) {
            return false;
        }
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
