package com.bindstone.dataprovider.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

@Component
public class TransportImportRunnable implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransportImportRunnable.class);

    private final ObjectMapper objectMapper;
    private final Sender topicSender;

    public TransportImportRunnable(ObjectMapper objectMapper, Sender topicSender) {
        this.objectMapper = objectMapper;
        this.topicSender = topicSender;
    }

    @Override
    public void run() {
        LOGGER.info ("Import Transports...");

        final Mono<OutboundMessage> messages = Mono.just(new OutboundMessage("com.bindstone1.topic.motorshop", "load", "Test".getBytes()));

        topicSender
                .send(messages)
                .doOnError(e -> LOGGER.error("Send failed", e))
                .subscribe();

    }
}
