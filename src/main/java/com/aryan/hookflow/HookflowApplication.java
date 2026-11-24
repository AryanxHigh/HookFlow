
package com.aryan.hookflow;


import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableScheduling
@SpringBootApplication
public class HookflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(HookflowApplication.class, args);
    }
}
