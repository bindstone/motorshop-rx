package com.bindstone.chatservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AliceBotServiceTest {

    @Autowired
    private AliceBotService aliceBotService;

    @Test
    public void helloTest() {
        String response = aliceBotService.respond("Hello Alice").block();
        System.out.println("Response:"+response);
        Assertions.assertNotNull(response);
    }
}
