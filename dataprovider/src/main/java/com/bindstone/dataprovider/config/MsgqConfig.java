package com.bindstone.dataprovider.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.rabbitmq.*;

import javax.annotation.PreDestroy;

@Configuration
public class MsgqConfig {

    public static final String EXCHANGE_ROOT = "com.bindstone";
    public static final String EXCHANGE_RETRY = EXCHANGE_ROOT + ".1.retry.motorshop";
    public static final String EXCHANGE_DLX = EXCHANGE_ROOT + ".1.dlx.motorshop";
    public static final String EXCHANGE_TOPIC = EXCHANGE_ROOT + ".1.topic.motorshop";
    public static final String EXCHANGE_QUEUE = EXCHANGE_ROOT + ".1.queue.motorshop";

    public static final String TOPIC_LOAD_KEY = "load";

    private ObjectMapper objectMapper;

    public MsgqConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgqConfig.class);

    @Bean
    public Sender topicSender() {
        LOGGER.info("Creating Message Queues and Sender");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.useNio();

        Sender sender = RabbitFlux.createSender();

        sender.declare(ExchangeSpecification.exchange(EXCHANGE_TOPIC).type("topic"))
                .then(sender.declare(QueueSpecification.queue(EXCHANGE_QUEUE)))
                .then(sender.bind(BindingSpecification.binding(EXCHANGE_TOPIC, TOPIC_LOAD_KEY, EXCHANGE_QUEUE)))
                .subscribe(r -> System.out.println("Exchange and queue declared and bound"));

        return sender;
    }

    @PreDestroy
    public void preDestroy() {
        LOGGER.info("Closing Sender");
        topicSender().close();
    }
}
