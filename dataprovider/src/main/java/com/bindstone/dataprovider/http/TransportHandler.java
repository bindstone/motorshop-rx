package com.bindstone.dataprovider.http;

import com.bindstone.dataprovider.service.TransportImportRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;

@Component
public class TransportHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransportHandler.class);

    private final TransportImportRunnable transportImportRunnable;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public TransportHandler(TransportImportRunnable transportImportRunnable, ThreadPoolTaskScheduler threadPoolTaskScheduler) {

        this.transportImportRunnable = transportImportRunnable;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }

    public Mono<ServerResponse> topicTransports(ServerRequest request) {
        LOGGER.info("Topic Transports");

        if (threadPoolTaskScheduler.getActiveCount() == 0) {
            threadPoolTaskScheduler.execute(transportImportRunnable);
            return ServerResponse.status(HttpStatus.OK).build();
        } else {
            return ServerResponse.status(HttpStatus.ALREADY_REPORTED).build();
        }

    }

}
