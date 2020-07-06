package com.bindstone.chatservice.config;

import org.alicebot.ab.Bot;
import org.alicebot.ab.configuration.BotConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {

    @Value("${chatbot.path}")
    String path;

    @Bean
    public Bot alice() {
        return new Bot(BotConfiguration.builder()
                .name("alice")
                .path(path)
                .build()
        );
    }
}
