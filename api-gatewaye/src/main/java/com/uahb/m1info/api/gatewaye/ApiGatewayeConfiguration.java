package com.uahb.m1info.api.gatewaye;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayeConfiguration {

    @Bean //elle permet d'instancié directement
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/client/**")
                        .uri("lb://paiement-service"))
                .route(p -> p.path("/paiement/**")
                        .uri("lb://paiement-service"))
                .route(p -> p.path("/service/**")
                        .uri("lb://paiement-service"))
                .route(p -> p.path("/transaction/{transactionId}")
                        .uri("lb://transaction-service"))
                .route(p -> p.path("/compte/deposer/**")
                        .uri("lb://transaction-service"))
                //.route(p -> p.path("/login")
                //        .uri("lb://api-gateway"))
                .build();
    }
}
