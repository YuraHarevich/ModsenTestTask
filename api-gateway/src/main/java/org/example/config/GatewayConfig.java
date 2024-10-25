package org.example.config;

import lombok.AllArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@AllArgsConstructor
public class GatewayConfig {
    private final AuthFilter filter;
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("storage-service", r -> r.path("/storage/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://storage-service"))
                .route("auth-service", r -> r.path("/authentication/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-service"))
                .route("acc-service", r -> r.path("/accountant/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://accountant-service"))
                .build();
    }
}
