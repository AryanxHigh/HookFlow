package com.aryan.hookflow.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/api/v1/health")
    public String checkHealth() {
        return "HookFlow is running successfully!";
    }
}


