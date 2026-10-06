package com.shopivo.product.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductResponse(
    UUID id,
    String name,
    String slug,
    String description,
    BigDecimal price,
    Integer stock,
    String imageUrl,
    Boolean active,
    Instant createdAt,
    Instant updatedAt
) {}
