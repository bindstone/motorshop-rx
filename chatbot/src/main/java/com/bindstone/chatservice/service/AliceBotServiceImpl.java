package com.bindstone.chatservice.service;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AliceBotServiceImpl implements AliceBotService {

    private final Bot alice;
    private final Chat chatSession;

    public AliceBotServiceImpl(Bot alice) {
        this.alice = alice;
        this.chatSession = new Chat(alice);
    }

    @Override
    public Mono<String> respond(String input) {
        return Mono.create(monoSink -> {
            String response = chatSession.multisentenceRespond(input);
            monoSink.success(response);
        });
    }

}
