package com.bindstone.service;

import java.io.Serializable;

import com.bindstone.repository.MotorBikeRepository;
import com.bindstone.socket.MessageSocket;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GreetService implements Serializable {

    private final MessageSocket messageSocket;
    private final MotorBikeRepository motorBikeRepository;

    public GreetService(MessageSocket messageSocket, MotorBikeRepository motorBikeRepository) {
        this.messageSocket = messageSocket;
        this.motorBikeRepository = motorBikeRepository;
    }

    public Mono<String> greet(String texte) {
        return messageSocket.requestResponse(texte);
    }
}
