package com.example.microserver.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RouterGlobalFilter implements GlobalFilter {

    private Logger logger = LoggerFactory.getLogger(RouterGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //-> {} , mean variable will be print here
        logger.info(" Path of the request received -> {} \n status -> {}"
                ,exchange.getRequest().getPath()
                ,exchange.getResponse().getStatusCode());

        //Adding new header in response
        exchange.getResponse().getHeaders().add("GOOD-ONE","THANKS");
        logger.info(String.valueOf(exchange.getResponse().getHeaders()));

        return chain.filter(exchange);
    }
}
