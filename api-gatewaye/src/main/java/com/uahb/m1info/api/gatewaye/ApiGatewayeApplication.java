package com.uahb.m1info.api.gatewaye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayeApplication.class, args);
    }
}
