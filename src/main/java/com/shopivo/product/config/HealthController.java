package com.shopivo.product.config;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/")
    public Map<String, String> init() {
        return Map.of(
                "status", "ok",
                "message", "Welcome to Product API",
                "service", "shopivo-product-service",
                "language", "Java",
                "framework", "Spring Boot");
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("service", "product", "status", "ok");
    }
}
