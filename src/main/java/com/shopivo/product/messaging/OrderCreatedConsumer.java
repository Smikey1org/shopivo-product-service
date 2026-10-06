package com.shopivo.product.messaging;

import tools.jackson.databind.ObjectMapper;
import com.shopivo.product.repository.ProductRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedConsumer {

    private final ProductRepository repository;
    private final ObjectMapper mapper = new ObjectMapper();

    public OrderCreatedConsumer(ProductRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "product.order.created")
    public void consume(String message) throws Exception {
        OrderCreatedEvent event = mapper.readValue(message, OrderCreatedEvent.class);

        for (var item : event.items()) {
            repository.findById(item.productId()).ifPresent(product -> {
                int newStock = Math.max(0, product.getStock() - item.quantity());

                product.setStock(newStock);
                repository.save(product);
            });
        }
    }
}
