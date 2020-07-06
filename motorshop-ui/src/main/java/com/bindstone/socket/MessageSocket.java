package com.bindstone.socket;

import io.rsocket.SocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@Service
public class MessageSocket {
    private static final Logger log = LoggerFactory.getLogger(MessageSocket.class);
    private static final String CLIENT_ID = UUID.randomUUID().toString();

    private RSocketRequester rsocketRequester;
    private RSocketRequester.Builder rsocketRequesterBuilder;
    private RSocketStrategies rsocketStrategies;

    @Autowired
    public MessageSocket(RSocketRequester.Builder builder,
                         @Qualifier("rSocketStrategies") RSocketStrategies strategies) {
        this.rsocketRequesterBuilder = builder;
        this.rsocketStrategies = strategies;

        SocketAcceptor responder = RSocketMessageHandler.responder(rsocketStrategies, new ClientHandler());

        this.rsocketRequester = rsocketRequesterBuilder
                .setupRoute("shell-client")
                .setupData(CLIENT_ID)
                .rsocketConnector(connector -> connector.acceptor(responder))
                .connectTcp("localhost", 7000)
                .block();
    }


    public Mono<String> requestResponse(String question) {
        log.info("\nSending one request. Waiting for one response...");
        return this.rsocketRequester
                .route("request-response")
                .data(question)
                .retrieveMono(String.class);
    }
}

class ClientHandler {
    private static final Logger log = LoggerFactory.getLogger(ClientHandler.class);

    @MessageMapping("client-status")
    public Flux<String> statusUpdate(String status) {
        log.info("Connection {}", status);
        return Flux.interval(Duration.ofSeconds(5)).map(index -> String.valueOf(Runtime.getRuntime().freeMemory()));
    }
}
