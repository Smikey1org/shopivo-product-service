package com.shopivo.product.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Binding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String EXCHANGE = "shopivo.events";
    public static final String QUEUE = "product.order.created";

    @Bean
    DirectExchange shopivoExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    Queue orderCreatedQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean
    Binding orderCreatedBinding(
        Queue orderCreatedQueue,
        DirectExchange shopivoExchange
    ) {
        return BindingBuilder
            .bind(orderCreatedQueue)
            .to(shopivoExchange)
            .with("order.created");
    }
}
