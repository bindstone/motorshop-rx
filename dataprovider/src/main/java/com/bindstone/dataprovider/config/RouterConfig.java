package com.bindstone.dataprovider.config;

import com.bindstone.dataprovider.http.TransportHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> route(TransportHandler transportHandler) {

        return RouterFunctions
                .route(RequestPredicates.GET("/load")
                .and(RequestPredicates
                .accept(MediaType.TEXT_PLAIN)), transportHandler::topicTransports);
    }
}
