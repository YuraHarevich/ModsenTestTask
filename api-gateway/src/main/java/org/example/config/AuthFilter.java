package org.example.config;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.services.JwtService;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class AuthFilter implements GatewayFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final RouterValidator validator;
    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (validator.isSecured.test(request)) {
            try {

                //проверка токена
                if (request
                        .getHeaders()
                        .getOrEmpty(HEADER_NAME)
                        .isEmpty()) {
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }
                String authHeader = request
                        .getHeaders()
                        .getOrEmpty(HEADER_NAME)
                        .get(0);
                //если нету токена в запросе
                if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }

                String token = authHeader.substring(BEARER_PREFIX.length());
                if (jwtService.isTokenExpired(token)) {
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }

                //проверка прав доступа

                if((StringUtils.contains(request.getURI().getPath(),"storage/change")
                        ||StringUtils.contains(request.getURI().getPath(),"storage/delete"))
                && !jwtService.extractRole(token).equals("ROLE_ADMIN")){
                        return onError(exchange, HttpStatus.FORBIDDEN);
                }
            }catch (MalformedJwtException | SignatureException e) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }
        }
        return chain.filter(exchange);
    }
    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
}
