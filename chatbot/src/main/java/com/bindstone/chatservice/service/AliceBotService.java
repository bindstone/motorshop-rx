package com.bindstone.chatservice.service;

import reactor.core.publisher.Mono;

public interface AliceBotService {
    Mono<String> respond(String input);
}
