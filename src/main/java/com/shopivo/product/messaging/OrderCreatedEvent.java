package com.shopivo.product.messaging;

import java.util.List;
import java.util.UUID;

public record OrderCreatedEvent(
    UUID orderId,
    String userId,
    List<OrderItemEvent> items
) {
    public OrderCreatedEvent {
        items = List.copyOf(items);
    }
    public record OrderItemEvent(
        UUID productId,
        Integer quantity
    ) {}
}
