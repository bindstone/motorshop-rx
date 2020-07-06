package com.bindstone.chatservice.socket;

import com.bindstone.chatservice.service.AliceBotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionSocket {
    private static final Logger log = LoggerFactory.getLogger(QuestionSocket.class);

    private final List<RSocketRequester> clients = new ArrayList<>();
    private final AliceBotService aliceBotService;

    public QuestionSocket(AliceBotService aliceBotService) {
        this.aliceBotService = aliceBotService;
    }

    @MessageMapping("request-response")
    Mono<String> requestResponse(final String question) {
        log.info("Received question: {}", question);
        return aliceBotService.respond(question);
    }

    @PreDestroy
    void shutdown() {
        log.info("Detaching all remaining clients...");
        clients.stream().forEach(requester -> requester.rsocket().dispose());
        log.info("Shutting down.");
    }
}
