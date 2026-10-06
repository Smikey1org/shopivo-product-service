package com.shopivo.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;

public record ProductRequest(
    @NotBlank String name,
    @NotBlank String slug,
    String description,
    @NotNull @DecimalMin("0.00") BigDecimal price,
    @NotNull @Min(0) Integer stock,
    String imageUrl,
    Boolean active
) {}
