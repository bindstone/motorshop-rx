package com.bindstone.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RequestTransportService {

    private WebClient client = WebClient.create("http://localhost:8082");

    private Mono<ClientResponse> result = client.get()
            .uri("/load")
            .accept(MediaType.TEXT_PLAIN)
            .exchange();

    public Mono<Boolean> request() {
        return result
            .map(res -> res.statusCode().is2xxSuccessful());
    }

}
